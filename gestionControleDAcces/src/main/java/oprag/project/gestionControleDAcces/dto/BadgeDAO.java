package oprag.project.gestionControleDAcces.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.Badge;
import oprag.project.gestionControleDAcces.models.CertificatControl;
import oprag.project.gestionControleDAcces.models.Utilisateur;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeDAO {
    private Integer id;

    @NotBlank(message = "Le numéro du badge est obligatoire")
    @Size(max = 50, message = "Le numéro ne peut pas dépasser 50 caractères")
    private String numero;

    @NotBlank(message = "La validité est obligatoire")
    private String validite;

    @NotBlank(message = "Le numéro du parc est obligatoire")
    @Size(max = 50, message = "Le numéro du parc ne peut pas dépasser 50 caractères")
    private String numeroParc;

    @NotNull(message = "Le statut actif est obligatoire")
    private boolean active;

    private CertificatControlDAO certificatControl;
    private UtilisateurDAO inspecteur;

    public static BadgeDAO fromEntity(Badge badge) {
        if(badge==null){
            return null;
        }
        return BadgeDAO.builder()
                .id(badge.getId())
                .numero(badge.getNumero())
                .validite(badge.getValidite())
                .numeroParc(badge.getNumeroParc())
                .active(badge.isActive())
                .certificatControl(CertificatControlDAO.fromEntity(badge.getCertificatControl()))
                .inspecteur(UtilisateurDAO.fromEntity(badge.getInspecteur()))
                .build();

    }

    public static Badge toEntity(BadgeDAO badgeDAO) {
        if(badgeDAO==null){
            return null;
        }
        Badge badge = new Badge();
        badge.setId(badgeDAO.getId());
        badge.setNumero(badgeDAO.getNumero());
        badge.setValidite(badgeDAO.getValidite());
        badge.setNumeroParc(badgeDAO.getNumeroParc());
        badge.setActive(badgeDAO.isActive());
        badge.setCertificatControl(CertificatControlDAO.toEntity(badgeDAO.getCertificatControl()));
        badge.setInspecteur(UtilisateurDAO.toEntity(badgeDAO.getInspecteur()));
        return badge;
    }
}
