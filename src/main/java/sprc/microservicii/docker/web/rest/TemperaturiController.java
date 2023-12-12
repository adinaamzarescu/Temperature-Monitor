package sprc.microservicii.docker.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprc.microservicii.docker.domain.Temperaturi;
import sprc.microservicii.docker.service.TemperaturiService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/temperatures")
public class TemperaturiController {

    private final TemperaturiService service;

    @Autowired
    public TemperaturiController(TemperaturiService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Integer> addTemperature(@RequestBody Temperaturi temperatura) {
        try {
            Temperaturi createdTemperature = service.createTemperaturi(temperatura);
            return new ResponseEntity<>(createdTemperature.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Temperaturi>> getTemperatures(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate until) {
        try {
            List<Temperaturi> temperatures = service.getTemperaturiByFilter(lat, lon, from, until);
            return new ResponseEntity<>(temperatures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cities/{id_oras}")
    public ResponseEntity<List<Temperaturi>> getTemperaturesForCity(
            @PathVariable Integer id_oras,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate until) {
        try {
            List<Temperaturi> temperatures = service.getTemperaturiByCityAndFilter(id_oras, from, until);
            return new ResponseEntity<>(temperatures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/countries/{id_tara}")
    public ResponseEntity<List<Temperaturi>> getTemperaturesForCountry(
            @PathVariable Integer id_tara,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate until) {
        try {
            List<Temperaturi> temperatures = service.getTemperaturiByCountryAndFilter(id_tara, from, until);
            return new ResponseEntity<>(temperatures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTemperature(@PathVariable Integer id, @RequestBody Temperaturi temperatura) {
        try {
            Temperaturi updatedTemperature = service.updateTemperaturi(id, temperatura);
            if (updatedTemperature != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete Temperature
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemperature(@PathVariable Integer id) {
        try {
            service.deleteTemperaturi(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
