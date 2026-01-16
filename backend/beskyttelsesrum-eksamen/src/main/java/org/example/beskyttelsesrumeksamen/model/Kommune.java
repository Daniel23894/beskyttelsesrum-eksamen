package org.example.beskyttelsesrumeksamen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Kommune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String navn;

    @OneToMany(mappedBy = "kommune", cascade = CascadeType.ALL)
    @JsonIgnore // For at undgå uendelig rekursion ved serialisering til JSON
    private List <Beskyttelsesrum> beskyttelsesrumListe;

    public Kommune(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public List<Beskyttelsesrum> getBeskyttelsesrumListe() {
        return beskyttelsesrumListe;
    }

    public void setBeskyttelsesrumListe(List<Beskyttelsesrum> beskyttelsesrumListe) {
        this.beskyttelsesrumListe = beskyttelsesrumListe;
    }

    @Override
    public String toString() {
        return "Kommune{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                '}';
    }
}
