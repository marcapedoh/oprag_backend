package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.CertificatControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CertificatControlRepository extends JpaRepository<CertificatControl,Integer> {
    List<CertificatControl> findCertificatControlByUtilisateurId(Integer id);
    List<CertificatControl> findCertificatControlByChauffeurId(Integer id);
    List<CertificatControl> findCertificatControlByVehiculeId(Integer id);
    @Query("SELECT u.inspection.nom AS inspectionName, COUNT(b) AS totalCards " +
            "FROM CertificatControl b JOIN b.utilisateur u JOIN u.inspection i " +
            "GROUP BY u.inspection.nom")
    List<Object[]> countCertificatControlByInspection();

    @Query("SELECT i.nom AS inspectionName, MONTH(certif.creationDate) AS mois, COUNT(certif) AS total " +
            "FROM CertificatControl certif " +
            "JOIN certif.utilisateur u " +
            "JOIN u.inspection i " +
            "GROUP BY i.nom, MONTH(certif.creationDate) " +
            "ORDER BY i.nom ASC, MONTH(certif.creationDate) ASC")
    List<Object[]> countCertificatControlByInspectionAndMonth();

    Optional<CertificatControl> findTopByUtilisateurIdOrderByCreationDateDesc(Integer utilisateurId);

    @Query("SELECT COALESCE(SUM(c.montant), 0) FROM CertificatControl c")
    Double getTotalMontant();


}
