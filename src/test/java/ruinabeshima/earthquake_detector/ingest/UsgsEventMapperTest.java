package ruinabeshima.earthquake_detector.ingest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import ruinabeshima.earthquake_detector.entity.EarthquakeEvent;
import ruinabeshima.earthquake_detector.usgs.UsgsFeedResponse;

public class UsgsEventMapperTest {
  UsgsFeedResponse.Properties testProperties = new UsgsFeedResponse.Properties(
      7.0, "South-West London", 1000L, 2000L, "reviewed");

  UsgsFeedResponse.Geometry testGeometry =
      new UsgsFeedResponse.Geometry(List.of(1.0, 2.0, 5.0));

  UsgsFeedResponse.Feature testFeature =
      new UsgsFeedResponse.Feature("test-id", testProperties, testGeometry);

  UsgsEventMapper testMapper = new UsgsEventMapper();

  @Test
  public void testMappingFunctionality() {
    EarthquakeEvent testEvent = testMapper.toEntity(testFeature);

    // Longitude and latitude order
    assertThat(testEvent.getLatitude()).isEqualTo(2.0);
    assertThat(testEvent.getLongitude()).isEqualTo(1.0);

    // Time format
    assertThat(testEvent.getOccurredAt())
        .isEqualTo(Instant.ofEpochMilli(1000L));
    assertThat(testEvent.getSourceUpdatedAt())
        .isEqualTo(Instant.ofEpochMilli(2000L));

    // id, Mag, place, statu
    assertThat(testEvent.getId()).isEqualTo("test-id");
    assertThat(testEvent.getMagnitude()).isEqualTo(7.0);
    assertThat(testEvent.getPlace()).isEqualTo("South-West London");
    assertThat(testEvent.getStatus()).isEqualTo("reviewed");
    assertThat(testEvent.getDepth()).isEqualTo(5.0);
  }
}
