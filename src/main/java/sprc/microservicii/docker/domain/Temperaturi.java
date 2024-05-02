package sprc.microservicii.docker.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Temperaturi", schema = "tema2")
public class Temperaturi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valoare", nullable = false)
    private Double valoare;

    @Column(name = "timestamp", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_oras", referencedColumnName = "id")
    private Orase idOras;

    public Temperaturi() {

    }

    public Temperaturi(Integer id, Double valoare, LocalDateTime timestamp, Orase oras) {
        this.id = id;
        this.valoare = valoare;
        this.timestamp = timestamp;
        this.idOras = oras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Orase getIdOras() {
        return idOras;
    }

    public void setIdOras(Orase oras) {
        this.idOras = oras;
    }
}
