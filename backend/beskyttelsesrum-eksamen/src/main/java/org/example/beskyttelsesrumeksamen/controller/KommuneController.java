package org.example.beskyttelsesrumeksamen.controller;

import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.example.beskyttelsesrumeksamen.service.KommuneService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/komunner")
@CrossOrigin(origins = "*") //tillader frontend: localhost:5500, hente data fra en anden adresse som backend fra: localhost:8080
public class KommuneController {
    private final KommuneService kommuneService;

    public KommuneController(KommuneService kommuneService) {
        this.kommuneService = kommuneService;
    }

    @GetMapping
    List<Kommune> getAllKommuner(){
        return kommuneService.findAll();
    }
}
