package covid.maps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import covid.maps.service.CovidParser;

import java.io.IOException;

@Controller
public class MapController {

    private final CovidParser covidParser;

    @Autowired
    public MapController(CovidParser covidParser) {
        this.covidParser = covidParser;
    }

    @GetMapping
    public String getMap(Model model) throws IOException {
        model.addAttribute("data", covidParser.getCovidData());
        return "map";
    }
}
