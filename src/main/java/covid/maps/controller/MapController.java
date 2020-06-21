package covid.maps.controller;

import covid.maps.service.points.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    private final PointsService pointsService;

    @Autowired
    public MapController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping
    public String getMap(Model model) {
        model.addAttribute("data", pointsService.getListPoints());
        return "map";
    }
}
