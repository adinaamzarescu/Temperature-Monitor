package sprc.microservicii.docker.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Temperaturi", schema = "tema2")
public class Temperaturi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valoare", nullable = false)
    private Double valoare;

    @Column(name = "timestamp", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "id_oras", referencedColumnName = "id")
    private Orase id_oras;

    public Temperaturi() {

    }
    public Temperaturi(Integer id, Double valoare, LocalDate timestamp, Orase oras) {
        this.id = id;
        this.valoare = valoare;
        this.timestamp = timestamp;
        this.id_oras = oras;
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

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = timestamp.format(formatter);
        this.timestamp = LocalDate.parse(formattedDate, formatter);
    }

    public Orase getId_oras() {
        return id_oras;
    }

    public void setId_oras(Orase oras) {
        this.id_oras = oras;
    }
}
