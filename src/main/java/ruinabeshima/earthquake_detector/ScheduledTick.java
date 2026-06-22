package ruinabeshima.earthquake_detector;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTick {
    @Scheduled(fixedRate = 1000)
    public void fixedRate(){
        System.out.println("Tick");
    }
}
