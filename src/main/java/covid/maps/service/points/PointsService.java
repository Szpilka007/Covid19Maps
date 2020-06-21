package covid.maps.service.points;

import covid.maps.model.Point;
import covid.maps.model.entity.CovidDataRecord;
import covid.maps.repository.CovidDataRepository;
import covid.maps.service.analize.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PointsService {

    private final CovidDataRepository covidDataRepository;
    private final Analyzer analyzer;

    @Value("${number.days.back.to.show.on.map}")
    private int amountBackDays;

    @Autowired
    public PointsService(CovidDataRepository covidDataRepository, Analyzer analyzer) {
        this.covidDataRepository = covidDataRepository;
        this.analyzer = analyzer;
    }

    public List<Point> getListPoints() {
        List<Point> pointList = new ArrayList<>();

        this.covidDataRepository.findAll().stream().filter(covidDataRecord -> covidDataRecord.getDay().equals(LocalDate.now().minusDays(amountBackDays)))
                    .forEach(covidDataRecord -> pointList.add(Point.builder()
                            .lat(covidDataRecord.getLat())
                            .lon(covidDataRecord.getLon())
                            .text(this.createDescription(covidDataRecord))
                            .build()));

        return pointList;
    }

    public String createDescription(CovidDataRecord covidDataRecord){
//        List<Analisis> analyses = this.analyzer.getAnalysisForActualData(this.covidDataRepository.findAll());
        String text = covidDataRecord.getCountryRegion() + " "
                + covidDataRecord.getProvince()
                + " Confirmed cases: " + covidDataRecord.getAmountConfirmedCases() + " "
                + " Death cases: " + covidDataRecord.getAmountDeathCases() + " "
                + " Recovered cases: " + covidDataRecord.getAmountRecoveredCases();
        return text;
    }
}
