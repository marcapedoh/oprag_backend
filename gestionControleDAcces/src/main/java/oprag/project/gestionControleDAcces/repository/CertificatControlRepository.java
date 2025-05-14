package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.CertificatControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificatControlRepository extends JpaRepository<CertificatControl,Integer> {
    List<CertificatControl> findCertificatControlByUtilisateurId(Integer id);
}
