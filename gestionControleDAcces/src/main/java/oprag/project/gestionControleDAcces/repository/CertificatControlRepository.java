package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.CertificatControl;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


    // Filtrage des certificats
    @Query("""
    SELECT c 
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
      AND (:inspectionId IS NULL OR c.utilisateur.inspection.id = :inspectionId)
""")
    List<CertificatControl> findFiltered(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("inspectionId") Long inspectionId
    );

    // Nombre conformes / non conformes par jour
    @Query("""
    SELECT c.creationDate, 
           SUM(CASE WHEN c.avisFavorable = true THEN 1 ELSE 0 END),
           SUM(CASE WHEN c.avisFavorable = false THEN 1 ELSE 0 END)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
      AND (:inspectionId IS NULL OR c.utilisateur.inspection.id = :inspectionId)
    GROUP BY c.creationDate
    ORDER BY c.creationDate
""")
    List<Object[]> getStatusStats(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("inspectionId") Long inspectionId
    );

    @Query("""
    SELECT c.creationDate, 
           SUM(CASE WHEN c.avisFavorable = true THEN 1 ELSE 0 END),
           SUM(CASE WHEN c.avisFavorable = false THEN 1 ELSE 0 END)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
    GROUP BY c.creationDate
    ORDER BY c.creationDate
    """)
    List<Object[]> getStatusStatsInit(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // Tendances inspections (total par jour)
    @Query("""
    SELECT c.creationDate, COUNT(c)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
      AND (:inspectionId IS NULL OR c.utilisateur.inspection.id = :inspectionId)
    GROUP BY c.creationDate
    ORDER BY c.creationDate
""")
    List<Object[]> getTrendStats(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("inspectionId") Long inspectionId
    );

    @Query("""
    SELECT c.creationDate, COUNT(c)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
    GROUP BY c.creationDate
    ORDER BY c.creationDate
""")
    List<Object[]> getTrendStatsInit(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // Répartition par type de véhicule
    @Query("""
    SELECT c.vehicule.typeVehicules, COUNT(c)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
      AND (:inspectionId IS NULL OR c.utilisateur.inspection.id = :inspectionId)
    GROUP BY c.vehicule.typeVehicules
    """)
    List<Object[]> getVehicleTypeStats(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("inspectionId") Long inspectionId
    );

    @Query("""
    SELECT c.vehicule.typeVehicules, COUNT(c)
    FROM CertificatControl c
    WHERE (c.creationDate >= :dateDebut)
      AND (c.creationDate <= :dateFin)
    GROUP BY c.vehicule.typeVehicules
    """)
    List<Object[]> getVehicleTypeStatsInit(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );
    // Rapports par jour et inspection
    @Query("""
    SELECT ri.utilisateur.inspection.nom, 
           FUNCTION('DATE', ri.creationDate), 
           COUNT(ri)
    FROM CertificatControl ri
    WHERE ri.creationDate BETWEEN :dateDebut AND :dateFin
    GROUP BY ri.utilisateur.inspection.nom, FUNCTION('DATE', ri.creationDate)
    ORDER BY FUNCTION('DATE', ri.creationDate), ri.utilisateur.inspection.nom
""")
    List<Object[]> countRapportsByDayAndInspection(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // Nombre de certificats conformes
    @Query("""
    SELECT COUNT(cc)
    FROM CertificatControl cc
    WHERE cc.avisFavorable = true
      AND cc.utilisateur.inspection.id = :inspectionId
      AND cc.creationDate BETWEEN :dateDebut AND :dateFin
""")
    Long countCertificatControlByAvisFavorable(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("inspectionId") Long inspectionId
    );

    @Query("""
    SELECT COUNT(cc)
    FROM CertificatControl cc
    WHERE cc.avisFavorable = true
      AND cc.creationDate BETWEEN :dateDebut AND :dateFin
""")
    Long countCertificatControlByAvisFavorableInit(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // Filtrage par date et inspection (méthode dérivée)
    List<CertificatControl> findCertificatControlByCreationDateBetweenAndUtilisateurInspectionId(
            LocalDate dateDebut,
            LocalDate dateFin,
            int inspectionId
    );

    List<CertificatControl> findCertificatControlByCreationDateBetween(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    // Répartition par société
    @Query("""
    SELECT c.societe, COUNT(c)
    FROM CertificatControl c
    GROUP BY c.societe
    ORDER BY COUNT(c) DESC
""")
    List<Object[]> countVehiculesBySociete();

    // Dernier numéro de rapport
    @Query("SELECT c.numeroRapport FROM CertificatControl c ORDER BY c.id DESC")
    String findTopByOrderByIdDesc();



    List<CertificatControl> findAllByCreationDateBetweenAndUtilisateurInspectionId(LocalDate dateDebut, LocalDate dateFin, int inspectionId);

    List<CertificatControl> findAllByCreationDateBetween(LocalDate dateDebut, LocalDate dateFin);





}
