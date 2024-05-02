package sprc.microservicii.docker.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprc.microservicii.docker.domain.Tari;
import sprc.microservicii.docker.repository.TariRepository;
import sprc.microservicii.docker.service.TariService;

import java.util.List;
import java.util.Optional;

@Service
public class TariServiceImpl implements TariService {

    private final TariRepository tariRepository;

    @Autowired
    public TariServiceImpl(TariRepository tariRepository) {
        this.tariRepository = tariRepository;
    }

    @Override
    public List<Tari> getAllTari() {
        return tariRepository.findAll();
    }

    @Override
    public Tari getTariById(Integer id) throws Exception {
        Optional<Tari> optionalTari = tariRepository.findById(id);
        return optionalTari.orElseThrow(() -> new Exception("Country not found with ID: " + id));
    }

    @Override
    public Tari createTari(Tari tari) throws Exception {
        if (tariRepository.findByNume(tari.getNume()).isPresent()) {
            throw new Exception("Duplicate entry for country name");
        }
        return tariRepository.save(tari);
    }

    @Override
    public Tari updateTari(Integer id, Tari updatedTari) throws Exception {
        Tari existingTari = getTariById(id);
        if (tariRepository.findByNumeAndIdNot(updatedTari.getNume(), id).isPresent()) {
            throw new Exception("Duplicate entry for country name");
        }

        existingTari.setNume(updatedTari.getNume());
        existingTari.setLat(updatedTari.getLat());
        existingTari.setLon(updatedTari.getLon());
        return tariRepository.save(existingTari);
    }

    @Override
    public void deleteTari(Integer id) throws Exception {
        Tari tari = getTariById(id);
        tariRepository.delete(tari);
    }
}
