package org.example.beskyttelsesrumeksamen.config;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.example.beskyttelsesrumeksamen.repository.BeskyttelsesrumRepository;
import org.example.beskyttelsesrumeksamen.repository.KommuneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    private final KommuneRepository kommuneRepository;
    private final BeskyttelsesrumRepository beskyttelsesrumRepository;

    public InitData(KommuneRepository kommuneRepository, BeskyttelsesrumRepository beskyttelsesrumRepository) {
        this.kommuneRepository = kommuneRepository;
        this.beskyttelsesrumRepository = beskyttelsesrumRepository;
    }

    @Override
    public void run(String... args) {
        // TRIN 1: Opret de 10 kommuner hvis de ikke findes
        if (kommuneRepository.count() == 0) {
            List<String> navne = List.of(
                    "København", "Aarhus", "Odense", "Aalborg", "Esbjerg",
                    "Randers", "Kolding", "Horsens", "Vejle", "Roskilde"
            );

            for (String navn : navne) {
                Kommune k = new Kommune();
                k.setNavn(navn);
                kommuneRepository.save(k);
            }
            System.out.println(" 10 Kommuner er oprettet ");
        }

        // TRIN 2: Opret rum og tilknyt dem til kommunerne via ID
        if (beskyttelsesrumRepository.count() == 0){
            // 1. København (ID 1)
            opretRum("Rådhuspladsen 1", 500, 55.6761, 12.5683, 1L);
            opretRum("Amagerbrogade 10", 250, 55.6612, 12.6055, 1L);
            opretRum("Nørrebrogade 50", 300, 55.6888, 12.5592, 1L);

            // 2. Aarhus (ID 2)
            opretRum("Dokk1", 300, 56.1536, 10.2143, 2L);
            opretRum("Aarhus Banegård", 150, 56.1502, 10.2045, 2L);

            // 3. Odense (ID 3)
            opretRum("Flakhaven 2", 200, 55.3959, 10.3883, 3L);
            opretRum("Odense Slot", 100, 55.3995, 10.3875, 3L);

            // 4. Aalborg (ID 4)
            opretRum("Budolfi Plads", 150, 57.0482, 9.9194, 4L);

            // 5. Esbjerg (ID 5)
            opretRum("Torvet 1", 120, 55.4670, 8.4521, 5L);

            // 6. Randers (ID 6)
            opretRum("Rådhustorvet 1", 100, 56.4607, 10.0364, 6L);

            // 7. Kolding (ID 7)
            opretRum("Akseltorv 1", 80, 55.4913, 9.4722, 7L);

            // 10. Roskilde (ID 10)
            opretRum("Stændertorvet 1", 140, 55.6419, 12.0878, 10L);
            opretRum("Roskilde Domkirke", 100, 55.6425, 12.0815, 10L);

            System.out.println("--- Rum data er oprettet ---");
        }
    }

    private void opretRum(String adresse, int kapacitet, double lat, double lon, Long kommuneId) {
        kommuneRepository.findById(kommuneId).ifPresent(kommune -> {
            Beskyttelsesrum rum = new Beskyttelsesrum();
            rum.setAdresse(adresse);
            rum.setKapacitet(kapacitet);
            rum.setLatitude(lat);
            rum.setLongitude(lon);
            rum.setKommune(kommune);
            beskyttelsesrumRepository.save(rum);
        });
    }
}