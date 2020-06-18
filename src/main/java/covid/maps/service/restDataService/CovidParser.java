package covid.maps.service.restDataService;

import covid.maps.model.CovidDataRecord;
import covid.maps.repository.CovidDataRepository;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidParser {

    private final CovidDataRepository covidDataRepository;
    private RestData restData;
    private int amountBackDays = 7;

    @Autowired
    public CovidParser(RestData restData,
                       CovidDataRepository covidDataRepository) {
        this.restData = restData;
        this.covidDataRepository = covidDataRepository;
    }

    public void loadDataToRepository() throws IOException {

        CSVParser confirmedParsedData = this.restData.getConfirmedData();
        CSVParser deathParsedData = this.restData.getDeathData();
        CSVParser recoveredParsedData = this.restData.getRecoveredData();

        List<CovidDataRecord> covidDataRecords = new ArrayList<>();

        for (int i = 1; i <= amountBackDays; i++) {

            LocalDate day = LocalDate.now().minusDays(i);

            for (CSVRecord confirmedRecord : confirmedParsedData) {
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

            deathParsedData.getRecords()
                    .forEach(deathRecord ->
                            covidDataRecords.forEach(covidDataRecord -> {
                                if (Double.parseDouble(deathRecord.get("Lat")) == covidDataRecord.getLat() &&
                                        Double.parseDouble(deathRecord.get("Long")) == covidDataRecord.getLon()) {
                                    covidDataRecord.setAmountDeathCases(Integer.parseInt(deathRecord.get(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))));
                                }
                            }));

            recoveredParsedData.getRecords()
                    .forEach(recoveredRecord ->
                            covidDataRecords.forEach(covidDataRecord -> {
                                if (Double.parseDouble(recoveredRecord.get("Lat")) == covidDataRecord.getLat() &&
                                        Double.parseDouble(recoveredRecord.get("Long")) == covidDataRecord.getLon()) {
                                    covidDataRecord.setAmountRecoveredCases(Integer.parseInt(recoveredRecord.get(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))));
                                }
                            }));

            this.covidDataRepository.addListRecords(covidDataRecords);
        }
    }

}
