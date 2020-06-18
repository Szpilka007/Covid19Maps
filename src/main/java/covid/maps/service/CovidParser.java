package covid.maps.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import covid.maps.model.Point;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidParser {

    private final RestTemplate restTemplate;

    @Autowired
    public CovidParser() {
        this.restTemplate = new RestTemplate();
    }

    public List<Point> getCovidData() throws IOException {
        List<Point> pointList = new ArrayList<>();
        String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        String covidData = this.restTemplate.getForObject(url, String.class);

        assert covidData != null;
        StringReader stringReader = new StringReader(covidData);
        CSVParser parserData = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord string : parserData) {
                pointList.add(new Point(Double.parseDouble(string.get("Lat")),Double.parseDouble(string.get("Long")),string.get("6/17/20")));
        }
        return pointList;
    }
}
