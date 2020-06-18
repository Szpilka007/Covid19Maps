package covid.maps.repository;

import covid.maps.model.CovidDataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CovidDataRepository {

    private final List<CovidDataRecord> list;

    @Autowired
    public CovidDataRepository() {
        this.list = new ArrayList<>();
    }

    public boolean addRecord(CovidDataRecord covidDataRecord){
        return this.list.add(covidDataRecord);
    }

    public boolean addListRecords(List<CovidDataRecord> covidDataRecordList){
        return this.list.addAll(covidDataRecordList);
    }

    public List<CovidDataRecord> getAllRecords(){
        return this.list;
    }
}
