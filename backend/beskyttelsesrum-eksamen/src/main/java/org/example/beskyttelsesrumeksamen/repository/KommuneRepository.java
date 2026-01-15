package org.example.beskyttelsesrumeksamen.repository;

import org.example.beskyttelsesrumeksamen.model.Kommune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KommuneRepository extends JpaRepository<Kommune, Long> {
}
