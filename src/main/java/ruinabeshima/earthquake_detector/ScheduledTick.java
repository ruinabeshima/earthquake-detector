package ruinabeshima.earthquake_detector;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ruinabeshima.earthquake_detector.client.UsgsClient;

@Component
public class ScheduledTick {
  private final UsgsClient usgsClient;

  public ScheduledTick(UsgsClient usgsClient) { this.usgsClient = usgsClient; }

  @Scheduled(fixedDelay = 60000)
  public void fixedDelay() {
    System.out.println(usgsClient.fetchFeed().features().size());
  }
}
