package sprc.microservicii.docker.service;

import sprc.microservicii.docker.domain.Orase;

import java.util.List;

public interface OraseService {

    List<Orase> getAllOrase();

    Orase getOraseById(Integer id) throws Exception;

    Orase createOrase(Orase oras) throws Exception;

    Orase updateOrase(Integer id, Orase oras) throws Exception;

    void deleteOrase(Integer id);

    public List<Orase> getOraseByCountry(Integer id_Tara);
}
