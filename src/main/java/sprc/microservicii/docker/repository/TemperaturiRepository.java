package sprc.microservicii.docker.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprc.microservicii.docker.domain.Tari;
import sprc.microservicii.docker.domain.Orase;
import sprc.microservicii.docker.domain.Temperaturi;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Transactional
public interface TemperaturiRepository extends JpaRepository<Temperaturi, Integer>, JpaSpecificationExecutor<Temperaturi> {

    @Query("SELECT t FROM Temperaturi t JOIN t.id_oras o WHERE o.lat = :lat AND o.lon = :lon AND t.timestamp BETWEEN :from AND :until")
    List<Temperaturi> findByLatLonAndDateRange(@Param("lat") Double lat, @Param("lon") Double lon, @Param("from") LocalDate from, @Param("until") LocalDate until);

    @Query("SELECT t FROM Temperaturi t WHERE t.id_oras.id = :idOras AND t.timestamp BETWEEN :from AND :until")
    List<Temperaturi> findByOrasIdAndTimestampBetween(@Param("idOras") Integer idOras, @Param("from") LocalDate from, @Param("until") LocalDate until);

    @Query("SELECT t FROM Temperaturi t JOIN t.id_oras o JOIN o.idTara ta WHERE ta.id = :idTara AND t.timestamp BETWEEN :from AND :until")
    List<Temperaturi> findByCountryAndDateRange(@Param("idTara") Integer idTara, @Param("from") LocalDate from, @Param("until") LocalDate until);

}

