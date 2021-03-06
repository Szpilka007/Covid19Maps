package covid.maps.service.scheduler;


import covid.maps.service.updater.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@EnableScheduling
public class ScheduledCovidDataService {

    private final CovidDataService covidDataService;

    @Autowired
    public ScheduledCovidDataService(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @Scheduled(cron = "0 0 7 * * ? ")
    public void loadActualDataToDatabase() throws IOException {
        covidDataService.loadActualDataToDatabase();
    }
}
