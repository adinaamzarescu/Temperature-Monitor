package sprc.microservicii.docker.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.service.OraseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class OraseController {

    private final OraseService service;

    @Autowired
    public OraseController(OraseService oraseService) {
        this.service = oraseService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> addCity(@RequestBody Orase oras) {
        Orase createdOras = service.createOrase(oras);
        Map<String, Integer> response = new HashMap<>();
        response.put("id", createdOras.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Orase>> getAllCities() {
        List<Orase> cities = service.getAllOrase();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/country/{id_Tara}")
    public ResponseEntity<List<Orase>> getCitiesByCountry(@PathVariable Integer id_Tara) {
        List<Orase> cities = service.getOraseByCountry(id_Tara);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable Integer id, @RequestBody Orase oras) {
        Orase updatedOras = service.updateOrase(id, oras);
        if (updatedOras != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
        service.deleteOrase(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


