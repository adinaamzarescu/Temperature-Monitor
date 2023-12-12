package sprc.microservicii.docker.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.repository.OraseRepository;
import sprc.microservicii.docker.service.OraseService;

import java.util.List;
import java.util.Optional;

@Service
public class OraseServiceImpl implements OraseService {

    private final OraseRepository oraseRepository;

    @Autowired
    public OraseServiceImpl(OraseRepository oraseRepository) {
        this.oraseRepository = oraseRepository;
    }

    @Override
    public List<Orase> getAllOrase() {
        return oraseRepository.findAll();
    }

    @Override
    public Orase getOraseById(Integer id) {
        Optional<Orase> optionalOrase = oraseRepository.findById(id);
        return optionalOrase.orElse(null);
    }

    @Override
    public Orase createOrase(Orase oras) {
        return oraseRepository.save(oras);
    }

    @Override
    public Orase updateOrase(Integer id, Orase oras) {
        if (oraseRepository.existsById(id)) {
            oras.setId(id);
            return oraseRepository.save(oras);
        }
        return null;
    }

    @Override
    public void deleteOrase(Integer id) {
        if (oraseRepository.existsById(id)) {
            oraseRepository.deleteById(id);
        }
    }

    @Override
    public List<Orase> getOraseByCountry(Integer id_Tara) {
        return oraseRepository.findByIdTara_Id(id_Tara);
    }
}
