/*
    Maps one USGS feed Feature (DTO) to one EarthquakeEvent (entity).

    toEntity():
      - Required (NOT NULL) fields go through the required-args constructor, so
        a half-built entity is impossible to construct.
      - Nullable fields (mag, place) are applied afterwards via setters.
      - version / createdAt / updatedAt are left untouched: Hibernate manages
        version, and created_at/updated_at are DB/trigger-owned (insertable=false).

    Two transformations worth calling out:
      - coordinates is a positional array [longitude, latitude, depth] — index 0
        is longitude, index 1 is latitude, index 2 is depth. Easy to swap; a
        unit test pins the order.
      - time / updated are epoch milliseconds (Long) and become Instants via
        Instant.ofEpochMilli.

    Handles one Feature only; looping the feed's features is the ingestion
    service's job.
*/

package ruinabeshima.earthquake_detector.ingest;

import java.time.Instant;
import org.springframework.stereotype.Component;
import ruinabeshima.earthquake_detector.entity.EarthquakeEvent;
import ruinabeshima.earthquake_detector.usgs.UsgsFeedResponse;

@Component
public class UsgsEventMapper {
  public EarthquakeEvent toEntity(UsgsFeedResponse.Feature feature) {

    // Define non-nullable fields using constructor
    EarthquakeEvent earthquakeEvent = new EarthquakeEvent(
        feature.id(), feature.properties().status(),

        feature.geometry().coordinates().get(1),
        feature.geometry().coordinates().get(0),
        feature.geometry().coordinates().get(2),
        Instant.ofEpochMilli(feature.properties().time()),
        Instant.ofEpochMilli(feature.properties().updated()));

    // Nullable fields
    earthquakeEvent.setMagnitude(feature.properties().mag());
    earthquakeEvent.setPlace(feature.properties().place());

    return earthquakeEvent;
  }
}
