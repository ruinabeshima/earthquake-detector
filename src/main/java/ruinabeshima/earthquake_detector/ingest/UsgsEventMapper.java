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
