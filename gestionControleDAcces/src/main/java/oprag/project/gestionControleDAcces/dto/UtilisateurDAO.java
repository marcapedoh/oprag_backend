package oprag.project.gestionControleDAcces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.models.Utilisateur;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurDAO {
    private Integer id;
    private String nom;
    private String prenom;
    private String userName;

    private String email;
    private String motDePasse;
    private boolean premiereConnexion;
    private Integer otpNumber;
    private String signature;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private InspectionDAO inspection;

    @JsonIgnore
    private List<BadgeDAO> badges;

    public static UtilisateurDAO fromEntity(Utilisateur utilisateur) {
        if(utilisateur == null) return null;

        return UtilisateurDAO.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .userName(utilisateur.getUsername())
                .premiereConnexion(utilisateur.isPremiereConnexion())
                .otpNumber(utilisateur.getOtpNumber())
                .active(utilisateur.isActive())
                .email(utilisateur.getEmail())
                .motDePasse(utilisateur.getMotDePasse())
                .signature(utilisateur.getSignature())
                .role(utilisateur.getRole())
                .inspection(InspectionDAO.fromEntity(utilisateur.getInspection()))
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDAO utilisateurDAO) {
        if(utilisateurDAO == null) return null;

        Utilisateur utilisateur=new Utilisateur();
        utilisateur.setId(utilisateurDAO.getId());
        utilisateur.setNom(utilisateurDAO.getNom());
        utilisateur.setPrenom(utilisateurDAO.getPrenom());
        utilisateur.setActive(utilisateurDAO.isActive());
        utilisateur.setPremiereConnexion(utilisateurDAO.isPremiereConnexion());
        utilisateur.setOtpNumber(utilisateurDAO.getOtpNumber());
        utilisateur.setUserName(utilisateurDAO.getUserName());
        utilisateur.setEmail(utilisateurDAO.getEmail());
        utilisateur.setSignature(utilisateurDAO.getSignature());
        utilisateur.setRole(utilisateurDAO.getRole());
        utilisateur.setInspection(InspectionDAO.toEntity(utilisateurDAO.getInspection()));
        return utilisateur;
    }
}
