package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import oprag.project.gestionControleDAcces.models.InspectionMontant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionMontantRepository extends JpaRepository<InspectionMontant,Integer> {
}
