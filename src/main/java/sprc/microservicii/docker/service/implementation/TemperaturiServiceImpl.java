package sprc.microservicii.docker.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sprc.microservicii.docker.domain.Temperaturi;
import sprc.microservicii.docker.repository.TemperaturiRepository;
import sprc.microservicii.docker.service.TemperaturiService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemperaturiServiceImpl implements TemperaturiService {

    private final TemperaturiRepository temperaturiRepository;

    @Autowired
    public TemperaturiServiceImpl(TemperaturiRepository temperaturiRepository) {
        this.temperaturiRepository = temperaturiRepository;
    }

    @Override
    public List<Temperaturi> getAllTemperaturi() {
        return temperaturiRepository.findAll();
    }

    @Override
    public Temperaturi getTemperaturiById(Integer id) throws Exception {
        Optional<Temperaturi> optionalTemperaturi = temperaturiRepository.findById(id);
        if (optionalTemperaturi.isEmpty()) {
            throw new Exception("No temperature record found with ID: " + id);
        }
        return optionalTemperaturi.get();
    }

    @Override
    public Temperaturi createTemperaturi(Temperaturi temperatura) throws Exception {
        if (temperatura.getTimestamp() == null) {
            temperatura.setTimestamp(LocalDateTime.now());
        }
        return temperaturiRepository.save(temperatura);
    }


    @Override
    public Temperaturi updateTemperaturi(Integer id, Temperaturi updatedTemperatura) throws Exception {
        try {
            Temperaturi existingTemperatura = getTemperaturiById(id);

            existingTemperatura.setValoare(updatedTemperatura.getValoare());
            existingTemperatura.setTimestamp(LocalDateTime.now());
            existingTemperatura.setIdOras(updatedTemperatura.getIdOras());

            return temperaturiRepository.save(existingTemperatura);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("temperaturi_unic")) {
                throw new Exception("Duplicate entry for temperature record at the same time and location");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void deleteTemperaturi(Integer id) throws Exception {
        Temperaturi existingTemperatura = getTemperaturiById(id);
        temperaturiRepository.delete(existingTemperatura);
    }

    @Override
    public List<Temperaturi> getTemperaturiByFilter(Double lat, Double lon, LocalDate from, LocalDate until) {
        List<Temperaturi> temperatures = temperaturiRepository.findAll();

        return temperatures.stream().filter(temperatura -> {
            boolean matchesLatLon = true;
            if (lat != null && lon != null) {
                matchesLatLon = temperatura.getIdOras() != null &&
                        temperatura.getIdOras().getLat().equals(lat) &&
                        temperatura.getIdOras().getLon().equals(lon);
            } else if (lat != null) {
                matchesLatLon = temperatura.getIdOras() != null &&
                        temperatura.getIdOras().getLat().equals(lat);
            } else if (lon != null) {
                matchesLatLon = temperatura.getIdOras() != null &&
                        temperatura.getIdOras().getLon().equals(lon);
            }

            boolean matchesDate = true;
            if (from != null && until != null) {
                LocalDate temperatureDate = temperatura.getTimestamp().toLocalDate();
                matchesDate = (!temperatureDate.isBefore(from)) && (!temperatureDate.isAfter(until));
            } else if (from != null) {
                LocalDate temperatureDate = temperatura.getTimestamp().toLocalDate();
                matchesDate = !temperatureDate.isBefore(from);
            } else if (until != null) {
                LocalDate temperatureDate = temperatura.getTimestamp().toLocalDate();
                matchesDate = !temperatureDate.isAfter(until);
            }

            return matchesLatLon && matchesDate;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Temperaturi> getTemperaturiByCityAndFilter(Integer idOras, LocalDate from, LocalDate until) {
        return temperaturiRepository.findAll().stream()
                .filter(t -> t.getIdOras().getId().equals(idOras) &&
                        (from == null || !t.getTimestamp().toLocalDate().isBefore(from)) &&
                        (until == null || !t.getTimestamp().toLocalDate().isAfter(until)))
                .collect(Collectors.toList());
    }


    @Override
    public List<Temperaturi> getTemperaturiByCountryAndFilter(Integer idTara, LocalDate from, LocalDate until) {
        return temperaturiRepository.findAll().stream()
                .filter(t -> t.getIdOras().getIdTara().getId().equals(idTara) &&
                        (from == null || !t.getTimestamp().toLocalDate().isBefore(from)) &&
                        (until == null || !t.getTimestamp().toLocalDate().isAfter(until)))
                .collect(Collectors.toList());
    }


}
