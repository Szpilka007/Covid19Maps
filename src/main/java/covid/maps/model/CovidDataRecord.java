package covid.maps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CovidDataRecord {

    private String countryRegion;
    private String province;
    private double lat;
    private double lon;
    private LocalDate day;
    private int amountConfirmedCases;
    private int amountDeathCases;
    private int amountRecoveredCases;

}
