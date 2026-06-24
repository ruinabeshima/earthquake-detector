/*
    Outbound HTTP client for the USGS feed.

    Constructor injection: the RestClient (the configured HTTP client, with
    timeouts) and FeedProperties (which feed URL to hit) are declared as
    constructor params and stored in final fields. Spring sees the single
    constructor and supplies both beans automatically — no @Autowired needed.

    fetchFeed() performs the call and parses the response to JSON. 
*/

package ruinabeshima.earthquake_detector.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ruinabeshima.earthquake_detector.config.FeedProperties;
import ruinabeshima.earthquake_detector.usgs.UsgsFeedResponse;

@Component
public class UsgsClient {

  private final RestClient restClient;
  private final FeedProperties feedProperties;

  public UsgsClient(RestClient restClient, FeedProperties feedProperties) {
    this.restClient = restClient;
    this.feedProperties = feedProperties;
  }

  public UsgsFeedResponse fetchFeed() {
    return restClient.get()
        .uri(feedProperties.url())
        .retrieve()
        .body(UsgsFeedResponse.class);
  }
}
