package sprc.microservicii.docker.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sprc.microservicii.docker.domain.Temperaturi;

@Repository
@Transactional
public interface TemperaturiRepository extends JpaRepository<Temperaturi, Integer>, JpaSpecificationExecutor<Temperaturi> {

}

