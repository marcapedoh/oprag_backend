package oprag.project.gestionControleDAcces.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.*;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeLicenceDAO {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;
    @Enumerated(EnumType.STRING)
    private StatutDemande statutDemande;
    private LocalDate dateSoumission;
    private String commentaire;
    private LicenceDAO licence;
    private UtilisateurDAO utilisateur;

    public static DemandeLicenceDAO fromEntity(DemandeLicence demandeLicence) {
        if (demandeLicence == null) {
            return null;
        }
        return  DemandeLicenceDAO.builder()
                .id(demandeLicence.getId())
                .typeDemande(demandeLicence.getTypeDemande())
                .statutDemande(demandeLicence.getStatutDemande())
                .dateSoumission(demandeLicence.getDateSoumission())
                .commentaire(demandeLicence.getCommentaire())
                .licence(LicenceDAO.fromEntity(demandeLicence.getLicence()))
                .utilisateur(UtilisateurDAO.fromEntity(demandeLicence.getUtilisateur()))
                .build();
    }

    public static DemandeLicence toEntity(DemandeLicenceDAO demandeLicence) {
        if (demandeLicence == null) {
            return null;
        }
        DemandeLicence demandeLicenceEntity = new DemandeLicence();
        demandeLicenceEntity.setId(demandeLicence.getId());
        demandeLicenceEntity.setTypeDemande(demandeLicence.getTypeDemande());
        demandeLicenceEntity.setStatutDemande(demandeLicence.getStatutDemande());
        demandeLicenceEntity.setDateSoumission(demandeLicence.getDateSoumission());
        demandeLicenceEntity.setCommentaire(demandeLicence.getCommentaire());
        demandeLicenceEntity.setLicence(LicenceDAO.toEntity(demandeLicence.getLicence()));
        demandeLicenceEntity.setUtilisateur(UtilisateurDAO.toEntity(demandeLicence.getUtilisateur()));
        return demandeLicenceEntity;
    }
}
