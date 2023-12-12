package sprc.microservicii.docker.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sprc.microservicii.docker.domain.Orase;

import java.util.List;

@Repository
@Transactional
public interface OraseRepository extends JpaRepository<Orase, Integer>, JpaSpecificationExecutor<Orase> {
    List<Orase> findByIdTara_Id(Integer idTara);

}
