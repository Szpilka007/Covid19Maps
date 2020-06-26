package covid.maps.service.analize;

import covid.maps.model.Analysis;
import covid.maps.model.entity.CovidDataRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Analyzer {

    public Analysis getAnalysisForActualData(String country, String province, List<CovidDataRecord> covidDataRecords) {

        List<CovidDataRecord> selectedRecords = covidDataRecords.stream()
                .filter(covidDataRecord ->
                        covidDataRecord.getProvince().equals(province) && covidDataRecord.getCountryRegion().equals(country))
                .collect(Collectors.toList());

        return Analysis.builder()
                .averageConfirmedPerDayCases(this.averageConfirmedCasesPerDay(selectedRecords))
                .averageRecoveredPerDayCases(this.averageRecoveredCasesPerDay(selectedRecords))
                .averageDeathPerDayCases(this.averageDeathCasesPerDay(selectedRecords))
                .averageActiveCasesPerDay(this.averageActiveCasesPerDay(selectedRecords))
                .build();
    }

    private int averageConfirmedCasesPerDay(List<CovidDataRecord> covidRecordsForCountry) {

        List<Integer> differencesConfirmedCases = new ArrayList<>();

        covidRecordsForCountry
                .forEach(covidDataRecord -> differencesConfirmedCases.add(
                        covidRecordsForCountry
                                .stream()
                                .filter(covidDataRecord1 ->
                                        covidDataRecord.getDay().minusDays(1).equals(covidDataRecord1.getDay()))
                                .findFirst()
                                .map(covidDataRecord1 -> covidDataRecord.getAmountConfirmedCases() - covidDataRecord1.getAmountConfirmedCases())
                                .orElse(0)));

        differencesConfirmedCases.remove(differencesConfirmedCases.size() - 1);

        return differencesConfirmedCases.stream()
                .reduce(Integer::sum)
                .map(sum -> sum / differencesConfirmedCases.size())
                .orElseThrow(() -> new RuntimeException("Incorrect data in confirmed cases"));

    }

    private int averageRecoveredCasesPerDay(List<CovidDataRecord> covidRecordsForCountry) {
        List<Integer> differencesRecoveredCases = new ArrayList<>();

        covidRecordsForCountry
                .forEach(covidDataRecord -> differencesRecoveredCases.add(
                        covidRecordsForCountry
                                .stream()
                                .filter(covidDataRecord1 ->
                                        covidDataRecord.getDay().minusDays(1).equals(covidDataRecord1.getDay()))
                                .findFirst()
                                .map(covidDataRecord1 -> covidDataRecord.getAmountRecoveredCases() - covidDataRecord1.getAmountRecoveredCases())
                                .orElse(0)));

        differencesRecoveredCases.remove(differencesRecoveredCases.size() - 1);

        return differencesRecoveredCases.stream()
                .reduce(Integer::sum)
                .map(sum -> sum / differencesRecoveredCases.size())
                .orElseThrow(() -> new RuntimeException("Incorrect data in confirmed cases"));
    }

    private int averageDeathCasesPerDay(List<CovidDataRecord> covidRecordsForCountry) {
        List<Integer> differencesDeathCases = new ArrayList<>();

        covidRecordsForCountry
                .forEach(covidDataRecord -> differencesDeathCases.add(
                        covidRecordsForCountry
                                .stream()
                                .filter(covidDataRecord1 ->
                                        covidDataRecord.getDay().minusDays(1).equals(covidDataRecord1.getDay()))
                                .findFirst()
                                .map(covidDataRecord1 -> covidDataRecord.getAmountDeathCases() - covidDataRecord1.getAmountDeathCases())
                                .orElse(0)));

        differencesDeathCases.remove(differencesDeathCases.size() - 1);

        return differencesDeathCases.stream()
                .reduce(Integer::sum)
                .map(sum -> sum / differencesDeathCases.size())
                .orElseThrow(() -> new RuntimeException("Incorrect data in confirmed cases"));
    }

    private int averageActiveCasesPerDay(List<CovidDataRecord> covidDataRecordsForCountry) {
        return covidDataRecordsForCountry.stream()
                .map(covidDataRecord -> covidDataRecord.getAmountConfirmedCases() - covidDataRecord.getAmountDeathCases() - covidDataRecord.getAmountRecoveredCases()).reduce(Integer::sum)
                .map(sum -> sum / covidDataRecordsForCountry.size())
                .orElseThrow(() -> new RuntimeException("Incorrect data in record cases"));
    }
}
