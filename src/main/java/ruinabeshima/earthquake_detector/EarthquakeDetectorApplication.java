package ruinabeshima.earthquake_detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class EarthquakeDetectorApplication {
	public static void main(String[] args) {
		SpringApplication.run(EarthquakeDetectorApplication.class, args);
	}

}
