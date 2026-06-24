package ruinabeshima.earthquake_detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ruinabeshima.earthquake_detector.config.FeedProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(FeedProperties.class)
public class EarthquakeDetectorApplication {
  public static void main(String[] args) {
    SpringApplication.run(EarthquakeDetectorApplication.class, args);
  }
}
