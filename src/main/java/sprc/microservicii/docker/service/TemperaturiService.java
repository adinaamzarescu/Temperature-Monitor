package sprc.microservicii.docker.service;

import sprc.microservicii.docker.domain.Temperaturi;

import java.time.LocalDate;
import java.util.List;

public interface TemperaturiService {

    List<Temperaturi> getAllTemperaturi();

    Temperaturi getTemperaturiById(Integer id) throws Exception;

    Temperaturi createTemperaturi(Temperaturi temperatura) throws Exception;

    List<Temperaturi> getTemperaturiByFilter(Double lat, Double lon, LocalDate from, LocalDate until);

    List<Temperaturi> getTemperaturiByCityAndFilter(Integer idOras, LocalDate from, LocalDate until);

    List<Temperaturi> getTemperaturiByCountryAndFilter(Integer idTara, LocalDate from, LocalDate until);

    Temperaturi updateTemperaturi(Integer id, Temperaturi temperatura) throws Exception;

    void deleteTemperaturi(Integer id) throws Exception;
}
