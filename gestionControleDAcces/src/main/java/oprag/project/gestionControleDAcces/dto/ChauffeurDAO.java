package oprag.project.gestionControleDAcces.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.CertificatControl;
import oprag.project.gestionControleDAcces.models.Chauffeur;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChauffeurDAO {
    private Integer id;
    private String numeroPermis;
    private String nom;
    private String prenom;
    private String numeroCertificatMedicalValide;
    private String attestionCapPoidsLourd;
    private String attestationDeConduiteDefensive;
    private String userName;
    private boolean active;
    private String motDePasse;

    @JsonIgnore
    private List<CertificatControlDAO> certificatControl;

    public static ChauffeurDAO fromEntity(Chauffeur chauffeur) {
        if(chauffeur == null) return null;

        return ChauffeurDAO.builder()
                .id(chauffeur.getId())
                .nom(chauffeur.getNom())
                .prenom(chauffeur.getPrenom())
                .numeroPermis(chauffeur.getNumeroPermis())
                .numeroCertificatMedicalValide(chauffeur.getNumeroCertificatMedicalValide())

                .attestationDeConduiteDefensive(chauffeur.getAttestationDeConduiteDefensive())
                .attestionCapPoidsLourd(chauffeur.getAttestionCapPoidsLourd())
                .userName(chauffeur.getUsername())
                .active(chauffeur.isActive())
                .motDePasse(chauffeur.getMotDePasse())
                .build();
    }

    public static Chauffeur toEntity(ChauffeurDAO chauffeurDAO) {
        if(chauffeurDAO == null) return null;

        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId(chauffeurDAO.getId());
        chauffeur.setNom(chauffeurDAO.getNom());
        chauffeur.setPrenom(chauffeurDAO.getPrenom());
        chauffeur.setNumeroPermis(chauffeurDAO.getNumeroPermis());
        chauffeur.setNumeroCertificatMedicalValide(chauffeurDAO.getNumeroCertificatMedicalValide());
        chauffeur.setAttestionCapPoidsLourd(chauffeurDAO.getAttestationDeConduiteDefensive());
        chauffeur.setUserName(chauffeurDAO.getUserName());
        chauffeur.setActive(chauffeurDAO.isActive());
        chauffeur.setMotDePasse(chauffeurDAO.getMotDePasse());
        chauffeur.setAttestationDeConduiteDefensive(chauffeurDAO.getAttestationDeConduiteDefensive());
        return chauffeur;
    }
}
