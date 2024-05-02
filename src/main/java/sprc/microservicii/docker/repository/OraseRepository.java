package sprc.microservicii.docker.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sprc.microservicii.docker.domain.Orase;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OraseRepository extends JpaRepository<Orase, Integer>, JpaSpecificationExecutor<Orase> {
    Optional<Orase> findByNume(String nume);

    @Query("SELECT o FROM Orase o WHERE o.nume = :nume AND o.id <> :id")
    Optional<Orase> findByNumeAndIdNot(String nume, Integer id);

    List<Orase> findByIdTara_Id(Integer idTara);
}
