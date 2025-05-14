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
    private String numeroCertificatMedicalValide;
    private String numeroVisiteTechnique;
    private String attestionCapPoidsLourd;
    private String attestationDeConduiteDefensive;

    @JsonIgnore
    private List<CertificatControlDAO> certificatControl;

    public static ChauffeurDAO fromEntity(Chauffeur chauffeur) {
        if(chauffeur == null) return null;

        return ChauffeurDAO.builder()
                .id(chauffeur.getId())
                .numeroPermis(chauffeur.getNumeroPermis())
                .numeroCertificatMedicalValide(chauffeur.getNumeroCertificatMedicalValide())
                .numeroVisiteTechnique(chauffeur.getNumeroVisiteTechnique())
                .attestationDeConduiteDefensive(chauffeur.getAttestationDeConduiteDefensive())
                .attestionCapPoidsLourd(chauffeur.getAttestionCapPoidsLourd())
                .build();
    }

    public static Chauffeur toEntity(ChauffeurDAO chauffeurDAO) {
        if(chauffeurDAO == null) return null;

        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId(chauffeurDAO.getId());
        chauffeur.setNumeroPermis(chauffeurDAO.getNumeroPermis());
        chauffeur.setNumeroVisiteTechnique(chauffeurDAO.getNumeroVisiteTechnique());
        chauffeur.setNumeroCertificatMedicalValide(chauffeurDAO.getNumeroCertificatMedicalValide());
        chauffeur.setAttestionCapPoidsLourd(chauffeurDAO.getAttestationDeConduiteDefensive());
        chauffeur.setAttestationDeConduiteDefensive(chauffeurDAO.getAttestationDeConduiteDefensive());
        return chauffeur;
    }
}
