package sprc.microservicii.docker.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprc.microservicii.docker.domain.Tari;

import java.util.Optional;

@Repository
@Transactional
public interface TariRepository extends JpaRepository<Tari, Integer>, JpaSpecificationExecutor<Tari> {
    Optional<Object> findByNume(String nume);

    @Query("SELECT t FROM Tari t WHERE t.nume = :nume AND t.id <> :id")
    Optional<Tari> findByNumeAndIdNot(@Param("nume") String nume, @Param("id") Integer id);
}
