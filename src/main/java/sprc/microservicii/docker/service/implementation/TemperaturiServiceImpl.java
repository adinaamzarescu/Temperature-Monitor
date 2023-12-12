package sprc.microservicii.docker.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprc.microservicii.docker.domain.Temperaturi;
import sprc.microservicii.docker.repository.OraseRepository;
import sprc.microservicii.docker.repository.TemperaturiRepository;
import sprc.microservicii.docker.service.TemperaturiService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TemperaturiServiceImpl implements TemperaturiService {

    private final TemperaturiRepository temperaturiRepository;
    private final OraseRepository oraseRepository;

    @Autowired
    public TemperaturiServiceImpl(TemperaturiRepository temperaturiRepository, OraseRepository oraseRepository) {
        this.temperaturiRepository = temperaturiRepository;
        this.oraseRepository = oraseRepository;
    }

    @Override
    public List<Temperaturi> getAllTemperaturi() {
        return temperaturiRepository.findAll();
    }

    @Override
    public Temperaturi getTemperaturiById(Integer id) {
        Optional<Temperaturi> optionalTemperaturi = temperaturiRepository.findById(id);
        return optionalTemperaturi.orElse(null);
    }

    @Override
    public Temperaturi createTemperaturi(Temperaturi temperatura) {
        temperatura.setTimestamp(LocalDate.now());
        return temperaturiRepository.save(temperatura);
    }

    @Override
    public List<Temperaturi> getTemperaturiByFilter(Double lat, Double lon, LocalDate from, LocalDate until) {
        return temperaturiRepository.findByLatLonAndDateRange(lat, lon, from, until);
    }

    @Override
    public List<Temperaturi> getTemperaturiByCityAndFilter(Integer idOras, LocalDate from, LocalDate until) {
        return temperaturiRepository.findByOrasIdAndTimestampBetween(idOras, from, until);
    }

    @Override
    public List<Temperaturi> getTemperaturiByCountryAndFilter(Integer idTara, LocalDate from, LocalDate until) {
        return temperaturiRepository.findByCountryAndDateRange(idTara, from, until);
    }

    @Override
    public Temperaturi updateTemperaturi(Integer id, Temperaturi temperatura) {
        if (temperaturiRepository.existsById(id)) {
            temperatura.setId(id);
            return temperaturiRepository.save(temperatura);
        }
        return null;
    }

    @Override
    public void deleteTemperaturi(Integer id) {
        if (temperaturiRepository.existsById(id)) {
            temperaturiRepository.deleteById(id);
        }
    }

}
