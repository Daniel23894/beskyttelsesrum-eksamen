package org.example.beskyttelsesrumeksamen.service;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.example.beskyttelsesrumeksamen.repository.BeskyttelsesrumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Transaktionsdata -> data der ændres ofte
@Service
public class BeskyttelsesrumService {

    private final BeskyttelsesrumRepository beskyttelsesrumRepository;

    public BeskyttelsesrumService(BeskyttelsesrumRepository beskyttelsesrumRepository){
        this.beskyttelsesrumRepository = beskyttelsesrumRepository;
    }

    // CRUD OPERATIONER
    public List<Beskyttelsesrum> findAll(){
        return beskyttelsesrumRepository.findAll();
    }

    public Optional<Beskyttelsesrum> findById(Long id){
        return beskyttelsesrumRepository.findById(id);
    }

    public List<Beskyttelsesrum> findByKommuneId(Long id) {
        return beskyttelsesrumRepository.findByKommuneId(id);
    }

    public Beskyttelsesrum save(Beskyttelsesrum beskyttelsesrum){
        return beskyttelsesrumRepository.save(beskyttelsesrum);
    }

    public void deleteById(Long id) {
        Optional<Beskyttelsesrum> beskyttelsesrumToDelete = beskyttelsesrumRepository.findById(id);
        if(beskyttelsesrumToDelete.isPresent()){
            beskyttelsesrumRepository.deleteById(id);
        } else {
            throw new RuntimeException("Beskyttelsesrum med id " + id + " findes ikke.");
        }
    }

    // DELOPGAVE 4
    // beregning af afstand mellem to koordinater (på en kugle/jorden) i km med Haversine formel
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Jordens radius i km

        // antal grader er der imellem brugerens position og beskyttelsesrummet
        // Konvertering af grader til radianer: konstant baseret på cirklens radius, påkrævet for Javas trigonometriske funktioner
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // Haversine formel
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        // vinkelafstanden i radianer mellem to punkter på en kugle/jorden
        // atan2: sikrer, at vi får den rigtige vinkel i forhold til jordens midte
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // radius * vinkelafstanden = afstand i kilometer langs jordens
        return earthRadius * c;
    }

}
