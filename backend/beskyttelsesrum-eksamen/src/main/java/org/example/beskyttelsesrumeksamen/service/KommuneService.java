package org.example.beskyttelsesrumeksamen.service;

import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.example.beskyttelsesrumeksamen.repository.KommuneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// ikke Transaktionsdata men Master Data -> faste rammer for opslagsværk
@Service
public class KommuneService {
    private final KommuneRepository kommuneRepository;

    public KommuneService(KommuneRepository kommuneRepository) {
        this.kommuneRepository = kommuneRepository;
    }

    public List<Kommune> findAll() {
        return kommuneRepository.findAll();
    }
}
