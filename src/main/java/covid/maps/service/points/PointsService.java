package covid.maps.service.points;

import covid.maps.model.Analysis;
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

        Analysis analysis =  this.analyzer.getAnalysisForActualData(covidDataRecord.getCountryRegion(),
                covidDataRecord.getProvince(),this.covidDataRepository.findAll());


        return "<b>" + covidDataRecord.getCountryRegion() + "</b><br> "
                + covidDataRecord.getProvince()
                + "<b>" + " Confirmed cases: " + "</b>" + covidDataRecord.getAmountConfirmedCases() + "<br>"
                + "<b>" + " Death cases: " + "</b>" +  covidDataRecord.getAmountDeathCases() + "<br> "
                + "<b>" + " Recovered cases: " + "</b>" +  covidDataRecord.getAmountRecoveredCases() + "<br>"
                + "<b>" + " Average confirmed cases last 7 days: " + "</b>" +  analysis.getAverageConfirmedPerDayCases() + "<br>"
                + "<b>" + " Average death cases last 7 days: " + "</b>" +  analysis.getAverageDeathPerDayCases() + "<br>"
                + "<b>" + " Average recovered cases last 7 days: " + "</b>" +  analysis.getAverageRecoveredPerDayCases() + "<br>"
                + "<b>" + " Average active cases last 7 days: " + "</b>" +  analysis.getAverageActiveCasesPerDay() + "<br>";

    }
}
