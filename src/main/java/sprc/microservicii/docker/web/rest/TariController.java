package sprc.microservicii.docker.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sprc.microservicii.docker.domain.Tari;
import sprc.microservicii.docker.service.TariService;
import sprc.microservicii.docker.service.implementation.TariServiceImpl;

import java.util.List;

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
                return new ResponseEntity<>("No Tari records found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(tariList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<?> addTara(@RequestBody Tari tara) {
        try {
            Tari createdTara = service.createTari(tara);
            return new ResponseEntity<>(createdTara, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Country with the same name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTara(@PathVariable Integer id, @RequestBody Tari tara) {
        try {
            Tari existingTara = service.getTariById(id);

            if (existingTara == null) {
                return new ResponseEntity<>("Country not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            Tari updatedTara = service.updateTari(id, tara);
            return new ResponseEntity<>(updatedTara, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Country error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTara(@PathVariable Integer id) {
        try {
            Tari existingTara = service.getTariById(id);

            if (existingTara == null) {
                return new ResponseEntity<>("Country not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            service.deleteTari(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.BAD_REQUEST);
        }
    }

}
