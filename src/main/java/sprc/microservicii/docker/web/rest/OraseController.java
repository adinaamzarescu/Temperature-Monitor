package sprc.microservicii.docker.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.domain.Tari;
import sprc.microservicii.docker.service.OraseService;
import sprc.microservicii.docker.service.TariService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class OraseController {

    private final OraseService service;
    private final TariService tariService;

    public OraseController(OraseService oraseService, TariService tariService) {
        this.service = oraseService;
        this.tariService = tariService;
    }

    @PostMapping()
    public ResponseEntity<?> addOras(@RequestBody Map<String, Object> requestData) {
        try {
            Integer idTara = Integer.parseInt(requestData.get("idTara").toString());
            Tari tara = tariService.getTariById(idTara);
            String nume = requestData.get("nume").toString();
            Double lat = Double.parseDouble(requestData.get("lat").toString());
            Double lon = Double.parseDouble(requestData.get("lon").toString());

            Orase oras = new Orase(null, tara, nume, lat, lon);
            Orase createdOras = service.createOrase(oras);
            Map<String, Integer> response = new HashMap<>();
            response.put("id", createdOras.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NumberFormatException | NullPointerException e) {
            return new ResponseEntity<>("Invalid input, check your data types.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("Country not found")) {
                return new ResponseEntity<>("Country not found with provided ID.", HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("Duplicate entry")) {
                return new ResponseEntity<>("Duplicate entry, city already exists.", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>("An unspecified error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAllOrase() {
        try {
            List<Orase> oraseList = service.getAllOrase();
            if (oraseList.isEmpty()) {
                return new ResponseEntity<>("No city records found", HttpStatus.NOT_FOUND);
            }
            List<Map<String, Object>> simplifiedOraseList = oraseList.stream().map(oras -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", oras.getId());
                map.put("idTara", oras.getIdTara().getId());
                map.put("nume", oras.getNume());
                map.put("lat", oras.getLat());
                map.put("lon", oras.getLon());
                return map;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(simplifiedOraseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred retrieving city data.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/country/{idTara}")
    public ResponseEntity<?> findOraseByCountry(@PathVariable Integer idTara) {
        try {
            List<Orase> oraseList = service.getOraseByCountry(idTara);
            if (oraseList.isEmpty()) {
                return new ResponseEntity<>("No cities found for the country ID: " + idTara, HttpStatus.NOT_FOUND);
            }
            List<Map<String, Object>> simplifiedOraseList = oraseList.stream().map(oras -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", oras.getId());
                map.put("idTara", oras.getIdTara().getId());
                map.put("nume", oras.getNume());
                map.put("lat", oras.getLat());
                map.put("lon", oras.getLon());
                return map;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(simplifiedOraseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while searching for cities by country.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOras(@PathVariable Integer id, @RequestBody Map<String, Object> requestData) {
        try {
            Integer idTara = Integer.parseInt(requestData.get("idTara").toString());
            Tari tara = tariService.getTariById(idTara);

            Integer jsonId = requestData.containsKey("id") ? Integer.parseInt(requestData.get("id").toString()) : null;
            if (jsonId == null) {
                return new ResponseEntity<>("The body is not complete. Missing ID.", HttpStatus.BAD_REQUEST);
            }
            if (!id.equals(jsonId)) {
                return new ResponseEntity<>("ID mismatch between URL and body", HttpStatus.BAD_REQUEST);
            }

            String nume = requestData.get("nume").toString();
            Double lat = Double.parseDouble(requestData.get("lat").toString());
            Double lon = Double.parseDouble(requestData.get("lon").toString());

            Orase oras = new Orase(id, tara, nume, lat, lon);
            Orase updatedOras = service.updateOrase(id, oras);
            if (updatedOras == null) {
                return new ResponseEntity<>("City not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("id", updatedOras.getId());
            map.put("idTara", updatedOras.getIdTara().getId());
            map.put("nume", updatedOras.getNume());
            map.put("lat", updatedOras.getLat());
            map.put("lon", updatedOras.getLon());

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NumberFormatException | NullPointerException e) {
            return new ResponseEntity<>("Invalid data provided", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("No city found")) {
                return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("Duplicate entry")) {
                return new ResponseEntity<>("Duplicate entry, city update conflicts with an existing city.", HttpStatus.CONFLICT);
            } else if (e.getMessage().contains("Country not found")) {
                return new ResponseEntity<>("Country not found for the given ID.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("An unspecified error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOras(@PathVariable Integer id) {
        try {
            service.getOraseById(id);
            service.deleteOrase(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e.getMessage().contains("No city found")) {
                return new ResponseEntity<>("City not found with provided ID.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("An error occurred while deleting the city.", HttpStatus.BAD_REQUEST);
        }
    }
}
