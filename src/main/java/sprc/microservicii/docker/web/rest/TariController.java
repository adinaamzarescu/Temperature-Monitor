package sprc.microservicii.docker.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprc.microservicii.docker.domain.Tari;
import sprc.microservicii.docker.service.TariService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
public class TariController {

    private final TariService service;

    public TariController(TariService tariService) {
        this.service = tariService;
    }

    @GetMapping()
    public ResponseEntity<?> findAllTari() {
        try {
            List<Tari> tariList = service.getAllTari();

            if (tariList.isEmpty()) {
                return new ResponseEntity<>("No country records found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(tariList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving countries: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> addTara(@RequestBody Tari tara) {
        try {
            Tari createdTara = service.createTari(tara);
            Map<String, Integer> response = new HashMap<>();
            response.put("id", createdTara.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTara(@PathVariable Integer id, @RequestBody Tari tara) {
        if (!id.equals(tara.getId())) {
            return new ResponseEntity<>("Mismatch between path ID and body ID", HttpStatus.BAD_REQUEST);
        }
        try {
            Tari updatedTara = service.updateTari(id, tara);
            return new ResponseEntity<>(updatedTara, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().contains("Country not found")) {
                return new ResponseEntity<>("Country not found with ID: " + id, HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("Duplicate entry")) {
                return new ResponseEntity<>("Duplicate entry for country name", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>("An error occurred while updating the country: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTara(@PathVariable Integer id) {
        try {
            service.getTariById(id);
            service.deleteTari(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e.getMessage().contains("Country not found")) {
                return new ResponseEntity<>("Country not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("An error occurred while deleting the country: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
