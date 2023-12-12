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
    public Tari getTariById(Integer id) {
        Optional<Tari> optionalTari = tariRepository.findById(id);
        return optionalTari.orElse(null);
    }

    @Override
    public Tari createTari(Tari tari) {
        return tariRepository.save(tari);
    }

    @Override
    public Tari updateTari(Integer id, Tari tari) {
        if (tariRepository.existsById(id)) {
            tari.setId(id);
            return tariRepository.save(tari);
        }
        return null;
    }

    @Override
    public void deleteTari(Integer id) {
        if (tariRepository.existsById(id)) {
            tariRepository.deleteById(id);
        }
    }
}
