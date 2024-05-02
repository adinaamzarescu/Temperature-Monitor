package sprc.microservicii.docker.service.implementation;

import org.springframework.stereotype.Service;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.repository.OraseRepository;
import sprc.microservicii.docker.service.OraseService;

import java.util.List;
import java.util.Optional;

@Service
public class OraseServiceImpl implements OraseService {

    private final OraseRepository oraseRepository;

    public OraseServiceImpl(OraseRepository oraseRepository) {
        this.oraseRepository = oraseRepository;
    }

    @Override
    public List<Orase> getAllOrase() {
        return oraseRepository.findAll();
    }

    @Override
    public Orase getOraseById(Integer id) throws Exception {
        Optional<Orase> optionalOrase = oraseRepository.findById(id);
        if (optionalOrase.isEmpty()) {
            throw new Exception("No city found with ID: " + id);
        }
        return optionalOrase.get();
    }

    @Override
    public Orase createOrase(Orase oras) throws Exception {
        if (oraseRepository.findByNume(oras.getNume()).isPresent()) {
            throw new Exception("Duplicate entry for city name");
        }
        return oraseRepository.save(oras);
    }

    @Override
    public Orase updateOrase(Integer id, Orase updatedOras) throws Exception {
        Optional<Orase> existingOras = oraseRepository.findById(id);
        if (existingOras.isEmpty()) {
            throw new Exception("No city found with ID: " + id);
        }

        Optional<Orase> conflictingCity = oraseRepository.findByNumeAndIdNot(updatedOras.getNume(), id);
        if (conflictingCity.isPresent()) {
            throw new Exception("Duplicate entry for city name");
        }

        existingOras.get().setNume(updatedOras.getNume());
        existingOras.get().setLat(updatedOras.getLat());
        existingOras.get().setLon(updatedOras.getLon());
        existingOras.get().setIdTara(updatedOras.getIdTara());
        return oraseRepository.save(existingOras.get());
    }

    @Override
    public void deleteOrase(Integer id) {
        oraseRepository.deleteById(id);
    }

    @Override
    public List<Orase> getOraseByCountry(Integer idTara) {
        return oraseRepository.findByIdTara_Id(idTara);
    }
}
