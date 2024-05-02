package sprc.microservicii.docker.web.rest;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.domain.Temperaturi;
import sprc.microservicii.docker.service.OraseService;
import sprc.microservicii.docker.service.TemperaturiService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/temperatures")
public class TemperaturiController {

    private final TemperaturiService service;
    private final OraseService oraseService;

    public TemperaturiController(TemperaturiService service, OraseService oraseService) {
        this.service = service;
        this.oraseService = oraseService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> addTemperature(@RequestBody Map<String, Object> requestData) {
        try {
            Integer idOras = Integer.parseInt(requestData.get("idOras").toString());
            Double valoare = Double.parseDouble(requestData.get("valoare").toString());

            Orase oras = oraseService.getOraseById(idOras);
            Temperaturi temperatura = new Temperaturi(null, valoare, LocalDateTime.now(), oras);
            Temperaturi createdTemperature = service.createTemperaturi(temperatura);

            Map<String, Integer> response = new HashMap<>();
            response.put("id", createdTemperature.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NumberFormatException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTemperature(@PathVariable Integer id) {
        try {
            Temperaturi temperatura = service.getTemperaturiById(id);
            return new ResponseEntity<>(temperatura, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Temperature record not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving the temperature", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTemperature(@PathVariable Integer id, @RequestBody Map<String, Object> requestData) {
        try {
            if (!requestData.containsKey("idOras") || !requestData.containsKey("valoare") || !requestData.containsKey("id")) {
                return new ResponseEntity<>("Missing required parameters", HttpStatus.BAD_REQUEST);
            }

            Integer bodyId = Integer.parseInt(requestData.get("id").toString());
            if (!id.equals(bodyId)) {
                return new ResponseEntity<>("ID mismatch between URL and body", HttpStatus.BAD_REQUEST);
            }

            Integer idOras = Integer.parseInt(requestData.get("idOras").toString());
            Double valoare = Double.parseDouble(requestData.get("valoare").toString());

            Orase oras = oraseService.getOraseById(idOras);
            if (oras == null) {
                return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
            }

            Temperaturi existingTemperature = service.getTemperaturiById(id);
            existingTemperature.setValoare(valoare);
            existingTemperature.setIdOras(oras);
            existingTemperature.setTimestamp(LocalDateTime.now());

            Temperaturi updatedTemperature = service.updateTemperaturi(id, existingTemperature);

            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedTemperature.getId());
            response.put("idOras", updatedTemperature.getIdOras().getId());
            response.put("valoare", updatedTemperature.getValoare());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid data provided", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during the update process", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemperature(@PathVariable Integer id) {
        try {
            service.deleteTemperaturi(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getTemperaturesByLatLon(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) {
        List<Temperaturi> temperatures = service.getTemperaturiByFilter(lat, lon, from, until);
        List<Map<String, Object>> simplifiedData = temperatures.stream().map(temp -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", temp.getId());
            data.put("valoare", temp.getValoare());
            data.put("timestamp", temp.getTimestamp());
            return data;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(simplifiedData, HttpStatus.OK);
    }

    @GetMapping("/cities/{id_oras}")
    public ResponseEntity<?> getTemperaturesByCity(
            @PathVariable Integer id_oras,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) {
        List<Temperaturi> temperatures = service.getTemperaturiByCityAndFilter(id_oras, from, until);
        List<Map<String, Object>> simplifiedData = temperatures.stream().map(temp -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", temp.getId());
            data.put("valoare", temp.getValoare());
            data.put("timestamp", temp.getTimestamp());
            return data;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(simplifiedData, HttpStatus.OK);
    }

    @GetMapping("/countries/{id_tara}")
    public ResponseEntity<?> getTemperaturesByCountry(
            @PathVariable Integer id_tara,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) {
        List<Temperaturi> temperatures = service.getTemperaturiByCountryAndFilter(id_tara, from, until);
        List<Map<String, Object>> simplifiedData = temperatures.stream().map(temp -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", temp.getId());
            data.put("valoare", temp.getValoare());
            data.put("timestamp", temp.getTimestamp());
            return data;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(simplifiedData, HttpStatus.OK);
    }
}
