package oprag.project.gestionControleDAcces.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class CertificatControl extends AbstractEntity{
    @Column(name = "site",nullable = false)
    private String site;
    @Column(name = "societe",nullable = false)
    private String societe;
    @Column(name = "numeroRapport",nullable = false)
    private String numeroRapport;
    @Column(name = "creationDate",nullable = false)
    private LocalDate creationDate;
    @Column(name = "localisationCertificationFait",nullable = false)
    private String localisationCertificationFait;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "noticeInstruction",nullable = false)
    private boolean noticeInstruction;
    @Column(name = "livretMaintenance",nullable = false)
    private boolean livretMaintenance;
    @Column(name = "declarationConformite",nullable = false)
    private boolean declarationConformite;
    @Column(name = "rapportControlePrecedent",nullable = false)
    private boolean rapportControlePrecedent;
    @Column(name = "declarationEcrite",nullable = false)
    private boolean declarationEcrite;
    @Column(name = "moyenAccess",nullable = false)
    private boolean moyenAccess;
    @Column(name = "moyenAccessPartiel",nullable = false)
    private boolean moyenAccessPartiel;
    @Column(name = "moyenAccessConducteur",nullable = false)
    private boolean moyenAccessConducteur;
    @Column(name = "essaiFonctionnementList",nullable = false)
    private List<EssaiFonctionnement> essaiFonctionnementList;
    @Column(name = "essaiNonFonctionnementList")
    private List<EssaiFonctionnement> essaiNonFonctionnementList;
    @Column(name = "conformeReglement",nullable = false)
    private boolean conformeReglement;
    @Column(name = "motifControle",nullable = false,length = 2500000)
    private String motifControle;
    @Column(name = "observationRecommendation",nullable = false,length = 2500000)
    private String observationRecommendation;
    @Column(name = "recommendation",length = 2500000)
    private String recommendation;
    @Column(name = "validite",nullable = false)
    @Enumerated(EnumType.STRING)
    private Validite validite;
    @Column(name = "signatureDGM",nullable = false)
    private String signatureDGM;
    @Column(name = "normeFabrication",nullable = false)
    private String normeFabrication;
    @Column(name = "paye",nullable = false)
    private boolean paye;
    @Column(name = "deleted",nullable = false)
    private boolean deleted;

    @Column(name = "avisFavorable")
    private Boolean avisFavorable;

    @Column(name = "certification_remorque")
    private Boolean certificationRemorque;
    @Column(name = "montant",nullable = false)
    private Double montant;
    @OneToOne(mappedBy = "certificatControl")
    private Badge badge;

    @ManyToOne
    @JoinColumn(name = "UtilisateurId")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "ChauffeurId")
    private Chauffeur chauffeur;

    @ManyToOne
    @JoinColumn(name = "VehiculeId")
    private Vehicule vehicule;
}
