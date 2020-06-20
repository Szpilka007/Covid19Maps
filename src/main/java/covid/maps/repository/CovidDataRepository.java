package covid.maps.repository;

import covid.maps.model.entity.CovidDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidDataRecord, Long> {

}
