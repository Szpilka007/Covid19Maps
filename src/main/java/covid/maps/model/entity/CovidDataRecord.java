package covid.maps.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "COVID_RECORDS")
public class CovidDataRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "COUNTRY_REGION")
    private String countryRegion;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "LATTITUDE")
    private double lat;

    @Column(name = "LONGTITUDE")
    private double lon;

    @Column(name = "DAY")
    private LocalDate day;

    @Column(name = "CONFIRMED_CASES")
    private int amountConfirmedCases;

    @Column(name = "DEATH_CASES")
    private int amountDeathCases;

    @Column(name = "RECOVERED_CASES")
    private int amountRecoveredCases;

}
