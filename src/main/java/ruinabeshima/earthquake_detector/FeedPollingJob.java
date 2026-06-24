/*
    The scheduled trigger for ingestion. Its only job is "on a timer, call
    ingest()" — it owns WHEN; IngestionService owns WHAT.

    fixedDelay (not fixedRate): the gap is measured end-to-start, so the next
   poll only begins after the previous one finishes — a slow fetch can't cause
   runs to stack up.
*/

package ruinabeshima.earthquake_detector;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ruinabeshima.earthquake_detector.ingest.IngestionService;

@Component
public class FeedPollingJob {
  private final IngestionService ingestionService;

  public FeedPollingJob(IngestionService ingestionService) {
    this.ingestionService = ingestionService;
  }

  @Scheduled(fixedDelayString = "${app.feed.interval}")
  public void fixedDelay() {
    ingestionService.ingest();
  }
}
