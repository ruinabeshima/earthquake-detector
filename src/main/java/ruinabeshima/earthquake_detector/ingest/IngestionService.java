/*
    Orchestrates the ingestion pipeline: fetch the feed, translate each
    feature, save each event.
    @Transactional on ingest(): wraps the whole batch in one transaction, so
    the poll lands fully or not at all. Without it, each save() auto-commits
    individually and a failure midway would leave a half-ingested feed.

    ingest():
      1. fetch the feed -> UsgsFeedResponse
      2. loop its features, mapping each to an EarthquakeEvent
      3. save each one
*/

package ruinabeshima.earthquake_detector.ingest;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruinabeshima.earthquake_detector.client.UsgsClient;
import ruinabeshima.earthquake_detector.entity.EarthquakeEvent;
import ruinabeshima.earthquake_detector.repository.EarthquakeEventRepository;
import ruinabeshima.earthquake_detector.usgs.UsgsFeedResponse;

@Service
public class IngestionService {
  private final UsgsClient usgsClient;
  private final UsgsEventMapper usgsEventMapper;
  private final EarthquakeEventRepository earthquakeEventRepository;

  public IngestionService(UsgsClient usgsClient,
                          UsgsEventMapper usgsEventMapper,
                          EarthquakeEventRepository earthquakeEventRepository) {
    this.usgsClient = usgsClient;
    this.usgsEventMapper = usgsEventMapper;
    this.earthquakeEventRepository = earthquakeEventRepository;
  }

  @Transactional
  public void ingest() {
    UsgsFeedResponse usgsResponse = usgsClient.fetchFeed();

    for (UsgsFeedResponse.Feature feature : usgsResponse.features()) {
      EarthquakeEvent earthquakeEvent = usgsEventMapper.toEntity(feature);
      earthquakeEventRepository.save(earthquakeEvent);
    }
  }
}
