package org.example.beskyttelsesrumeksamen.repository;

import org.example.beskyttelsesrumeksamen.model.Beskyttelsesrum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeskyttelsesrumRepository extends JpaRepository <Beskyttelsesrum, Long> {
}
