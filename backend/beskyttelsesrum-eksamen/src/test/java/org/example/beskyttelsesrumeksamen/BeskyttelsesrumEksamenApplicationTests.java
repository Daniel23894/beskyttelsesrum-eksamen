package org.example.beskyttelsesrumeksamen;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.example.beskyttelsesrumeksamen.repository.BeskyttelsesrumRepository;
import org.example.beskyttelsesrumeksamen.repository.KommuneRepository;
import org.example.beskyttelsesrumeksamen.service.BeskyttelsesrumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
class BeskyttelsesrumEksamenApplicationTests {

    @Autowired
    private BeskyttelsesrumService beskyttelsesrumService;

    @Autowired
    private BeskyttelsesrumRepository beskyttelsesrumRepository;

    @Autowired
    private KommuneRepository kommuneRepository;

    @Autowired
    private MockMvc mockMvc;

    // 1. UNIT TEST: matematikken (Delopgave 4)
    @Test
    void testAfstandLogik() {
        double dist = beskyttelsesrumService.calculateDistance(55.6761, 12.5683, 55.6730, 12.5650);
        assertTrue(dist > 0 && dist < 1.0, "Afstanden skal være korrekt beregnet");
    }

    // 2. DATA TEST: Tester JPA-relationen (Delopgave 1)
    @Test
    void testDatabaseRelation() {
        Kommune k = new Kommune();
        k.setNavn("TestKommune");
        Kommune gemtK = kommuneRepository.save(k);

        Beskyttelsesrum r = new Beskyttelsesrum();
        r.setAdresse("Testvej 1");
        r.setKommune(gemtK);
        beskyttelsesrumRepository.save(r);

        var liste = beskyttelsesrumRepository.findByKommuneId(gemtK.getId());
        assertEquals(1, liste.size(), "Repository skal kunne finde rum via Kommune ID");
    }

    // 3. API TEST: Tester at endpointet (Delopgave 2)
    @Test
    void testApiSvarer() throws Exception {
        mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk());
    }

    // 4. DELETE TEST: Verificerer sletning af et rum
    @Test
    void testDeleteRoom() {
        // 1. Opret og gem et rum
        Beskyttelsesrum rum = new Beskyttelsesrum();
        rum.setAdresse("Slet Mig Gade 1");
        Beskyttelsesrum gemtRum = beskyttelsesrumRepository.save(rum);
        Long id = gemtRum.getId();

        // 2. Slet rummet via repository
        beskyttelsesrumRepository.deleteById(id);

        // 3. Bekræft at det ikke findes længere
        assertTrue(beskyttelsesrumRepository.findById(id).isEmpty(), "Rummet skal være slettet fra databasen");
    }
}