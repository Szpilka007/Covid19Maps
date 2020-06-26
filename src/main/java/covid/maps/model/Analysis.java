package covid.maps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {

    private String countryRegion;
    private String province;
    private LocalDate day;
    private int averageConfirmedPerDayCases;
    private int averageRecoveredPerDayCases;
    private int averageDeathPerDayCases;
    private int averageActiveCasesPerDay;
    private int averageDegreeOfInfective;

}
