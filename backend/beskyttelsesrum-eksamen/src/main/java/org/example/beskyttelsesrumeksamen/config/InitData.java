package org.example.beskyttelsesrumeksamen.config;

import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.example.beskyttelsesrumeksamen.repository.KommuneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    private final KommuneRepository kommuneRepository;

    public InitData(KommuneRepository kommuneRepository){
        this.kommuneRepository = kommuneRepository;
    }

    @Override
    public void run (String... args){
        if(kommuneRepository.count() == 0){

            // one-liner, og listen er immutable
            List<String> navne = List.of(
                    "København",
                    "Aarhus",
                    "Odense",
                    "Aalborg",
                    "Esbjerg",
                    "Randers",
                    "Kolding",
                    "Horsens",
                    "Vejle",
                    "Roskilde"
            );

            navne.forEach(navn -> {
                Kommune kommune = new Kommune();
                kommune.setNavn(navn);
                kommuneRepository.save(kommune);
            });

            System.out.println("Initialiseret 10 Kommuner i databasen.");
        }
    }
}
