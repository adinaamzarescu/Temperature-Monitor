package sprc.microservicii.docker.service;

import sprc.microservicii.docker.domain.Tari;

import java.util.List;

public interface TariService {

    List<Tari> getAllTari();

    Tari getTariById(Integer id);

    Tari createTari(Tari tari);

    Tari updateTari(Integer id, Tari tari);

    void deleteTari(Integer id);
}