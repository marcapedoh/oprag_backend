package oprag.project.gestionControleDAcces.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificatControlDAO {
    private Integer id;
    private String site;
    private String societe;
    private String numeroRapport;
    private LocalDate creationDate;
    private String localisationCertificationFait;
    private String description;
    private boolean noticeInstruction;
    private boolean livretMaintenance;
    private Boolean certificationRemorque;
    private boolean declarationConformite;
    private boolean rapportControlePrecedent;
    private boolean declarationEcrite;
    private boolean moyenAccess;
    private boolean moyenAccessPartiel;
    private boolean moyenAccessConducteur;
    @Enumerated(EnumType.STRING)
    private List<EssaiFonctionnement> essaiFonctionnementList;
    @Enumerated(EnumType.STRING)
    private List<EssaiFonctionnement> essaiNonFonctionnementList;
    private boolean conformeReglement;
    private String motifControle;
    private String observationRecommendation;
    private String recommendation;
    private Validite validite;
    private String signatureDGM;
    private String normeFabrication;
    private Boolean avisFavorable;
    private boolean paye;
    private boolean deleted;
    private boolean status;
    private Double montant;
    @JsonIgnore
    private BadgeDAO badge;

    private UtilisateurDAO utilisateur;

    private ChauffeurDAO chauffeur;

    private VehiculeDAO vehicule;

    public static CertificatControlDAO fromEntity(CertificatControl certificatControl) {
        if (certificatControl == null) {
            return null;
        }

        return CertificatControlDAO.builder()
                .id(certificatControl.getId())
                .site(certificatControl.getSite())
                .societe(certificatControl.getSociete())
                .numeroRapport(certificatControl.getNumeroRapport())
                .deleted(certificatControl.isDeleted())
                .creationDate(certificatControl.getCreationDate())
                .validite(certificatControl.getValidite())
                .description(certificatControl.getDescription())
                .localisationCertificationFait(certificatControl.getLocalisationCertificationFait())
                .noticeInstruction(certificatControl.isNoticeInstruction())
                .livretMaintenance(certificatControl.isLivretMaintenance())
                .certificationRemorque(certificatControl.getCertificationRemorque())
                .declarationConformite(certificatControl.isDeclarationConformite())
                .rapportControlePrecedent(certificatControl.isRapportControlePrecedent())
                .declarationEcrite(certificatControl.isDeclarationEcrite())
                .moyenAccess(certificatControl.isMoyenAccess())
                .avisFavorable(certificatControl.getAvisFavorable())
                .moyenAccessPartiel(certificatControl.isMoyenAccessPartiel())
                .moyenAccessConducteur(certificatControl.isMoyenAccessConducteur())
                .essaiFonctionnementList(certificatControl.getEssaiFonctionnementList())
                .essaiNonFonctionnementList(certificatControl.getEssaiNonFonctionnementList())
                .conformeReglement(certificatControl.isConformeReglement())
                .motifControle(certificatControl.getMotifControle())
                .observationRecommendation(certificatControl.getObservationRecommendation())
                .recommendation(certificatControl.getRecommendation())
                .validite(certificatControl.getValidite())
                .signatureDGM(certificatControl.getSignatureDGM().isEmpty()?certificatControl.getSignatureDGM():"null")
                .normeFabrication(certificatControl.getNormeFabrication())
                .paye(certificatControl.isPaye())
                .status(certificatControl.isStatus())
                .montant(certificatControl.getMontant())
                //.badge(BadgeDAO.fromEntity(certificatControl.getBadge()))
                .utilisateur(UtilisateurDAO.fromEntity(certificatControl.getUtilisateur()))
                .chauffeur(ChauffeurDAO.fromEntity(certificatControl.getChauffeur()))
                .vehicule(VehiculeDAO.fromEntity(certificatControl.getVehicule()))

                .build();
    }

    public static CertificatControl toEntity(CertificatControlDAO certificatControl) {
        if (certificatControl == null) {
            return null;
        }

        CertificatControl certificatControlEntity = new CertificatControl();
        certificatControlEntity.setId(certificatControl.getId());
        certificatControlEntity.setSite(certificatControl.getSite());
        certificatControlEntity.setSociete(certificatControl.getSociete());
        certificatControlEntity.setCreationDate(certificatControl.getCreationDate());
        certificatControlEntity.setNumeroRapport(certificatControl.getNumeroRapport());
        certificatControlEntity.setValidite(certificatControl.getValidite());
        certificatControlEntity.setDescription(certificatControl.getDescription());
        certificatControlEntity.setDeleted(certificatControl.isDeleted());
        certificatControlEntity.setLocalisationCertificationFait(certificatControl.getLocalisationCertificationFait());
        certificatControlEntity.setNoticeInstruction(certificatControl.isNoticeInstruction());
        certificatControlEntity.setLivretMaintenance(certificatControl.isLivretMaintenance());
        certificatControlEntity.setCertificationRemorque(certificatControl.getCertificationRemorque());
        certificatControlEntity.setDeclarationConformite(certificatControl.isDeclarationConformite());
        certificatControlEntity.setRapportControlePrecedent(certificatControl.isRapportControlePrecedent());
        certificatControlEntity.setDeclarationEcrite(certificatControl.isDeclarationEcrite());
        certificatControlEntity.setMoyenAccess(certificatControl.isMoyenAccess());
        certificatControlEntity.setMoyenAccessPartiel(certificatControl.isMoyenAccessPartiel());
        certificatControlEntity.setMoyenAccessConducteur(certificatControl.isMoyenAccessConducteur());
        certificatControlEntity.setEssaiFonctionnementList(certificatControl.getEssaiFonctionnementList());
        certificatControlEntity.setEssaiNonFonctionnementList(certificatControl.getEssaiNonFonctionnementList());
        certificatControlEntity.setConformeReglement(certificatControl.isConformeReglement());
        certificatControlEntity.setAvisFavorable(certificatControl.getAvisFavorable());
        certificatControlEntity.setMotifControle(certificatControl.getMotifControle());
        certificatControlEntity.setObservationRecommendation(certificatControl.getObservationRecommendation());
        certificatControlEntity.setRecommendation(certificatControl.getRecommendation());
        certificatControlEntity.setValidite(certificatControl.getValidite());
        certificatControlEntity.setSignatureDGM(certificatControl.getSignatureDGM());
        certificatControlEntity.setNormeFabrication(certificatControl.getNormeFabrication());
        certificatControlEntity.setPaye(certificatControl.isPaye());
        certificatControlEntity.setStatus(certificatControl.isStatus());
        certificatControlEntity.setMontant(certificatControl.getMontant());
       //certificatControlEntity.setBadge(BadgeDAO.toEntity(certificatControl.getBadge()));
        certificatControlEntity.setUtilisateur(UtilisateurDAO.toEntity(certificatControl.getUtilisateur()));
        certificatControlEntity.setChauffeur(ChauffeurDAO.toEntity(certificatControl.getChauffeur()));
        certificatControlEntity.setVehicule(VehiculeDAO.toEntity(certificatControl.getVehicule()));
        return certificatControlEntity;
    }


}
