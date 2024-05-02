package sprc.microservicii.docker.domain;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "Tari", schema = "tema2")
public class Tari implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nume_tara", unique = true, nullable = false)
    private String nume;

    @Column(name = "latitudine", nullable = false)
    private Double lat;

    @Column(name = "longitudine", nullable = false)
    private Double lon;

    public Tari() {

    }

    public Tari(Integer id, String nume, Double lat, Double lon) {
        this.id = id;
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

    public String getNume() {
        return nume;
    }

    public void setNume(String numeTara) {
        this.nume = numeTara;
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