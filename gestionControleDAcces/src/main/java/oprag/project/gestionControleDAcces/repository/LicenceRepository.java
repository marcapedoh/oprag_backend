package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.Licence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenceRepository extends JpaRepository<Licence,Integer> {
    Licence findFirstByPaysOrderByIdDesc(String pays);
}
