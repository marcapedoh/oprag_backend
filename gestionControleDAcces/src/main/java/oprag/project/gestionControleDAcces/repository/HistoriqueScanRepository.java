package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.AuthenticationLog;
import oprag.project.gestionControleDAcces.models.HistoriqueScan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueScanRepository extends JpaRepository<HistoriqueScan,Integer> {
}
