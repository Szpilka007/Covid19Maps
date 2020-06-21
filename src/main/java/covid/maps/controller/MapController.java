package covid.maps.controller;

import covid.maps.service.points.PointsService;
import covid.maps.service.scheduler.ScheduledCovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MapController {

    private final PointsService pointsService;
    private final ScheduledCovidDataService scheduledCovidDataService;

    @Autowired
    public MapController(PointsService pointsService, ScheduledCovidDataService scheduledCovidDataService) {
        this.pointsService = pointsService;
        this.scheduledCovidDataService = scheduledCovidDataService;
    }

    @GetMapping
    public String getMap(Model model) throws IOException {
        scheduledCovidDataService.loadActualDataToDatabase();
        model.addAttribute("data", pointsService.getListPoints());
        return "map";
    }
}
