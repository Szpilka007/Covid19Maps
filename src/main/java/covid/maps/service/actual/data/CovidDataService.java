package covid.maps.service.actual.data;

import covid.maps.model.entity.CovidDataRecord;
import covid.maps.repository.CovidDataRepository;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CovidDataService {

    private final CovidDataRepository covidDataRepository;
    private RestData restData;

    @Value("${number.days.load.to.repository}")
    private int amountBackDays;


    @Autowired
    public CovidDataService(RestData restData,
                            CovidDataRepository covidDataRepository) {
        this.restData = restData;
        this.covidDataRepository = covidDataRepository;
    }

    public void loadActualDataToDatabase() throws IOException {
        covidDataRepository.deleteAll();
        this.loadDataToRepository();
    }

    public void loadDataToRepository() throws IOException {


        List<CovidDataRecord> covidDataRecords = new ArrayList<>();

        for (int i = 1; i <= amountBackDays; i++) {

            LocalDate day = LocalDate.now().minusDays(i);
            List<CSVParser> parsedData = Arrays.asList(this.restData.getConfirmedData(), this.restData.getDeathData(), this.restData.getRecoveredData());

            for (CSVRecord confirmedRecord : parsedData.get(0)) {
                covidDataRecords.add(
                        CovidDataRecord.builder()
                                .countryRegion(confirmedRecord.get("Country/Region"))
                                .province(confirmedRecord.get("Province/State"))
                                .lat(Double.parseDouble(confirmedRecord.get("Lat")))
                                .lon(Double.parseDouble(confirmedRecord.get("Long")))
                                .day(day)
                                .amountConfirmedCases(Integer.parseInt(confirmedRecord.get(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))))
                                .build());
            }

            parsedData.get(1).getRecords()
                    .forEach(deathRecord ->
                            covidDataRecords.forEach(covidDataRecord -> {
                                if (Double.parseDouble(deathRecord.get("Lat")) == covidDataRecord.getLat() &&
                                        Double.parseDouble(deathRecord.get("Long")) == covidDataRecord.getLon()) {
                                    covidDataRecord.setAmountDeathCases(Integer.parseInt(deathRecord.get(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))));
                                }
                            }));

            parsedData.get(2).getRecords()
                    .forEach(recoveredRecord ->
                            covidDataRecords.forEach(covidDataRecord -> {
                                if (Double.parseDouble(recoveredRecord.get("Lat")) == covidDataRecord.getLat() &&
                                        Double.parseDouble(recoveredRecord.get("Long")) == covidDataRecord.getLon()) {
                                    covidDataRecord.setAmountRecoveredCases(Integer.parseInt(recoveredRecord.get(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))));
                                }
                            }));

            this.covidDataRepository.saveAll(covidDataRecords);
        }
    }
}
