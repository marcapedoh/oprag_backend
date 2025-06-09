package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.CertificatControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificatControlRepository extends JpaRepository<CertificatControl,Integer> {
    List<CertificatControl> findCertificatControlByUtilisateurId(Integer id);
    List<CertificatControl> findCertificatControlByChauffeurId(Integer id);
    List<CertificatControl> findCertificatControlByVehiculeId(Integer id);
    Optional<CertificatControl> findTopByUtilisateurIdOrderByCreationDateDesc(Integer utilisateurId);

}
