package sprc.microservicii.docker.service;

import sprc.microservicii.docker.domain.Orase;

import java.util.List;

public interface OraseService {

    List<Orase> getAllOrase();

    Orase getOraseById(Integer id);

    Orase createOrase(Orase oras);

    Orase updateOrase(Integer id, Orase oras);

    void deleteOrase(Integer id);

    public List<Orase> getOraseByCountry(Integer id_Tara);
}
