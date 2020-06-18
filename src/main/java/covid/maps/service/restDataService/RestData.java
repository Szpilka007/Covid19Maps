package covid.maps.service.restDataService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

@Service
public class RestData {

    private final RestTemplate restTemplate;

    private String confirmedUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private String deathUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private String recoveredUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    public RestData() {
        this.restTemplate = new RestTemplate();
    }

    public CSVParser getConfirmedData() throws IOException {
        String covidData = this.restTemplate.getForObject(confirmedUrl, String.class);
        StringReader stringReader = new StringReader(Objects.requireNonNull(covidData));
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
    }

    public CSVParser getDeathData() throws IOException {
        String covidData = this.restTemplate.getForObject(deathUrl, String.class);
        StringReader stringReader = new StringReader(Objects.requireNonNull(covidData));
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
    }

    public CSVParser getRecoveredData() throws IOException {
        String covidData = this.restTemplate.getForObject(recoveredUrl, String.class);
        StringReader stringReader = new StringReader(Objects.requireNonNull(covidData));
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
    }
}
