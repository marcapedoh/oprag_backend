package oprag.project.gestionControleDAcces.repository;


import oprag.project.gestionControleDAcces.models.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection,Integer> {
    Optional<Inspection> findInspectionByNom(String nom);

    Optional<Inspection> findByCode(String code);
}
