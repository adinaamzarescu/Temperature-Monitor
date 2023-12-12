package sprc.microservicii.docker.domain;
import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "Orase", schema = "tema2")
public class Orase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tara", referencedColumnName = "id")
    private Tari idTara;

    @Column(name = "nume_oras", nullable = false)
    private String nume;

    @Column(name = "latitudine", nullable = false)
    private Double lat;

    @Column(name = "longitudine", nullable = false)
    private Double lon;

    public Orase() {

    }
    public Orase(Integer id, Tari tara, String nume, Double lat, Double lon) {
        this.id = id;
        this.idTara = tara;
        this.nume = nume;
        this.lat = lat;
        this.lon = lon;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tari getIdTara() {
        return idTara;
    }

    public void setIdTara(Tari tara) {
        this.idTara = tara;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String numeOras) {
        this.nume = numeOras;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double latitudine) {
        this.lat = latitudine;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double longitudine) {
        this.lon = longitudine;
    }
}

