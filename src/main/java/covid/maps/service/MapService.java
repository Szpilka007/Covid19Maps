package covid.maps.service;

import covid.maps.model.Point;
import covid.maps.repository.CovidDataRepository;
import covid.maps.service.restDataService.CovidParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {

    private final CovidParser covidParser;
    private final CovidDataRepository covidDataRepository;

    @Value("${number.days.back.to.show.on.map}")
    private int amountBackDays;

    @Autowired
    public MapService(CovidParser covidParser, CovidDataRepository covidDataRepository) {
        this.covidParser = covidParser;
        this.covidDataRepository = covidDataRepository;
    }

    public List<Point> getListPoints() throws IOException {
        this.covidParser.loadDataToRepository();
        List<Point> pointList = new ArrayList<>();

        this.covidDataRepository.getAllRecords().stream().filter(covidDataRecord -> covidDataRecord.getDay().equals(LocalDate.now().minusDays(amountBackDays)))
                    .forEach(covidDataRecord -> pointList.add(Point.builder()
                            .lat(covidDataRecord.getLat())
                            .lon(covidDataRecord.getLon())
                            .text(covidDataRecord.getCountryRegion() + " "
                                    + covidDataRecord.getProvince()
                                    + " Confirmed cases: " + covidDataRecord.getAmountConfirmedCases() + " "
                                    + " Death cases: " + covidDataRecord.getAmountDeathCases() + " "
                                    + " Recovered cases: " + covidDataRecord.getAmountRecoveredCases()
                            )
                            .build()));

        return pointList;
    }
}
