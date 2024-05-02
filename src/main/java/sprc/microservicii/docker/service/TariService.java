package sprc.microservicii.docker.service;

import sprc.microservicii.docker.domain.Tari;

import java.util.List;

public interface TariService {

    List<Tari> getAllTari();

    Tari getTariById(Integer id) throws Exception;

    public Tari createTari(Tari tari) throws Exception;

    Tari updateTari(Integer id, Tari tari) throws Exception;

    void deleteTari(Integer id) throws Exception;
}