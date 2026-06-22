package ruinabeshima.earthquake_detector.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import ruinabeshima.earthquake_detector.entity.EarthquakeEvent;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
public class EarthquakeEventRepositoryTest {

    // Define and start test Postgres container
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private EarthquakeEventRepository repository;

    @Test
    void savesAndFindsById() {

        // Build EarthquakeEvent via the required-fields constructor
        EarthquakeEvent testEarthquakeEvent = new EarthquakeEvent(
            "test-id",
            "reviewed",
            10.0,
            20.0,
            5.0,
            java.time.Instant.now(),
            java.time.Instant.now()
        );

        repository.save(testEarthquakeEvent);
        EarthquakeEvent found = repository.findById("test-id").orElseThrow();

        // Check correct values returned
        assertThat(found.getId()).isEqualTo("test-id");
        assertThat(found.getStatus()).isEqualTo("reviewed");
        assertThat(found.getLatitude()).isEqualTo(10.0);
        assertThat(found.getLongitude()).isEqualTo(20.0);
        assertThat(found.getDepth()).isEqualTo(5.0);
        assertThat((found.getVersion())).isEqualTo(0); // Version check
    }
}
