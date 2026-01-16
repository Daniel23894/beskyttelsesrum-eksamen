package org.example.beskyttelsesrumeksamen.repository;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeskyttelsesrumRepository extends JpaRepository <Beskyttelsesrum, Long> {

    List<Beskyttelsesrum> findByKommuneId(Long kommuneId);
}
