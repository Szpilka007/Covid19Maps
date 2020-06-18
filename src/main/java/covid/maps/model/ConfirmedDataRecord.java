package covid.maps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmedDataRecord {

    private String countryRegion;
    private double lat;
    private double lon;
    private int amountConfirmedCases;
}
