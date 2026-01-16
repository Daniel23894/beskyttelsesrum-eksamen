package org.example.beskyttelsesrumeksamen.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Beskyttelsesrum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresse;
    private int kapacitet;

    // Attributter til afstandsberegning
    private double latitude;  // Breddegrad: afstand nord/syd fra Ækvator (0°), fra 90°S til 90°N
    private double longitude; // Længdegrad: afstand øst/vest fra Prime Meridian (0°), fra 180°V til 180°Ø

    @ManyToOne
    @JoinColumn(name = "kommune_id") // Fremmednøglen i databasen
    private Kommune kommune;

    public Beskyttelsesrum() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Kommune getKommune() {
        return kommune;
    }

    public void setKommune(Kommune kommune) {
        this.kommune = kommune;
    }

    @Override
    public String toString() {
        return "Beskyttelsesrum{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", kapacitet=" + kapacitet +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
