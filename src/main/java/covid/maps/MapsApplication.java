package covid.maps;

import covid.maps.repository.CovidDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MapsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapsApplication.class, args);
    }

}
