package org.example.beskyttelsesrumeksamen.controller;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.example.beskyttelsesrumeksamen.repository.BeskyttelsesrumRepository;
import org.example.beskyttelsesrumeksamen.service.BeskyttelsesrumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BeskyttelsesrumController {
    private final BeskyttelsesrumService beskyttelsesrumService;

    public BeskyttelsesrumController(BeskyttelsesrumService beskyttelsesrumService){
        this.beskyttelsesrumService = beskyttelsesrumService;
    }

    // CRUD OPERATIONER
    // Opret et beskyttelsesrum
    @PostMapping("/rooms")
    public Beskyttelsesrum createRoom(@RequestBody Beskyttelsesrum beskyttelsesrum){
        return beskyttelsesrumService.save(beskyttelsesrum);
    }

    // Ændrer et beskyttelsesrum
    @PutMapping("/rooms/{roomId}")
    public Beskyttelsesrum updateRoom(@PathVariable Long roomId, @RequestBody Beskyttelsesrum rumDetails) {

        // 1. Find det eksisterende rum i databasen
        Beskyttelsesrum eksisterendeRum = beskyttelsesrumService.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Beskyttelsesrum med id " + roomId + " findes ikke."));

        // 2. Opdater felterne med de nye data fra frontend
        eksisterendeRum.setAdresse(rumDetails.getAdresse());
        eksisterendeRum.setKapacitet(rumDetails.getKapacitet());
        eksisterendeRum.setLatitude(rumDetails.getLatitude());
        eksisterendeRum.setLongitude(rumDetails.getLongitude());
        eksisterendeRum.setKommune(rumDetails.getKommune());

        // 3. Gem de opdaterede data
        return beskyttelsesrumService.save(eksisterendeRum);
    }

    // Lister alle beskyttelsesrum
    @GetMapping("/rooms/all")
    public List<Beskyttelsesrum> getAllRooms(){
        return beskyttelsesrumService.findAll();
    }

    // Lister alle rum for en bestemt kommune
    @GetMapping("/kommuner/{kommuneId}/rooms")
    public List<Beskyttelsesrum> getRoomsByKommune(@PathVariable Long kommuneId) {
        return beskyttelsesrumService.findByKommuneId(kommuneId);
    }

    // Slet et specifikt beskyttelsesrum
    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable Long id) {
        beskyttelsesrumService.deleteById(id);
    }

    // DELOPGAVE 4
    // Beregn afstanden fra brugerens position til et rum
    @GetMapping("/rooms/distance")
    public double getDistanceToRoom(@RequestParam double userLat,
                                    @RequestParam double userLon,
                                    @RequestParam Long roomId) {

        // 1. Hent rummet fra databasen for at få dets koordinater
        Beskyttelsesrum rum = beskyttelsesrumService.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Beskyttelsesrum ikke fundet."));

        // 2. Beregn afstanden med Haversine formel
        return beskyttelsesrumService.calculateDistance(
                userLat,
                userLon,
                rum.getLatitude(),
                rum.getLongitude()
        );
    }

}
