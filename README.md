# Earthquake Event Pipeline

A real-time event pipeline that ingests live earthquake data from the USGS GeoJSON feed into PostgreSQL. Built on Spring Boot as a study of reliable ingestion patterns. 

## Tech stack

- **Java 21** (LTS)
- **Spring Boot 4** (Spring Framework 7)
- **PostgreSQL 16**
- **Spring Data JPA** / Hibernate
- **Flyway** for database migrations
- **Maven** (via the bundled `./mvnw` wrapper)
- **JUnit 5** + **Testcontainers** for tests
- **Docker** (Compose) for the local database

## Pipeline

On a timer, the system pulls the latest earthquakes from USGS and lands them in
PostgreSQL:

1. A scheduled poller fetches the USGS feed on a configurable interval.
2. The GeoJSON response is parsed into Java objects.
3. Each earthquake is mapped from the feed's shape into a domain event.
4. Events are persisted to PostgreSQL, keyed by their USGS event id.

```
USGS GeoJSON feed
      │   scheduled poll (fixed delay)
      ▼
  Fetch over HTTP        (RestClient)
      ▼
  Parse GeoJSON          (Jackson → feed DTOs)
      ▼
  Map to domain event    (feed feature → EarthquakeEvent)
      ▼
  Persist to PostgreSQL  (keyed by USGS event id)
```

## Architecture / Design Decisions

- **The USGS event id is the primary key.** Earthquakes are revised after the
  fact — magnitude and review status get updated as analysis improves — so the
  same event reappears in the feed. Keying on the USGS id means an event is
  updated *in place* rather than inserted as a duplicate.

- **The schema models what the feed actually sends.** `magnitude` and `place`
  are nullable because some earthquake events have been observed to have omitted them; every other field is non-nullable. 

- **Optimistic locking via a `version` column.** 
    Concurrent (same-time) updates to a field in the database is extremely common as events are revised and not write-once. A version
  counter lets the database reject a stale update instead of silently letting
  one writer clobber another's changes.

- **The ingestion path is split into single-responsibility collaborators.**
  Fetching (`RestClient` -> `UsgsClient`), translating (a pure feed-to-entity mapper), and
  orchestration (a thin service) are separate. The scheduler only decides
  *when*; the service decides *what*. This keeps the most logic-heavy piece, the mapper, a pure function that's trivial to unit-test in isolation.

- **Configuration is externalized.** The feed URL and poll interval live in
  configuration, not in code, so the same build can target different feeds or
  cadences without recompiling.

- **Each poll is transactional.** A poll either persists its whole batch or
  none of it, so a failure partway through can't leave a half-ingested feed.

- **Tests run against real PostgreSQL via Testcontainers.** Rather than an
  in-memory stand-in, tests spin up the same database engine used in
  production, so migrations, constraints, and SQL behavior are genuinely
  exercised.



## Running locally

### Prerequisites

- Java 21
- Docker (with Docker Compose)

### 1. Start PostgreSQL

The local database runs in Docker:

```bash
docker compose up -d
```

This starts a `postgres:16-alpine` instance on `localhost:5432` with the
database, user, and password the app expects (see `compose.yaml`).

### 2. Run the application

```bash
./mvnw spring-boot:run
```

On startup, Flyway applies the database migrations, then the scheduled poller
begins fetching the USGS feed on an interval and persisting earthquake events.

### 3. Inspect the data

Connect to the running database and query the ingested events:

```bash
docker exec -it earthquake-detector-database-1 \
  psql -U username -d earthquake_detector_dev_db \
  -c "SELECT id, magnitude, place, occurred_at FROM earthquakes ORDER BY occurred_at DESC LIMIT 10;"
```

### Running the tests

```bash
./mvnw test
```

Tests use Testcontainers to spin up a throwaway PostgreSQL instance, so Docker
must be running. No separate database setup is required.
