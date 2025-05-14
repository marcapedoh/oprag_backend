package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class Chauffeur extends AbstractEntity {
    @Column(name = "numeroPermis",nullable = false)
    private String numeroPermis;
    @Column(name = "numeroCertificatMedicalValide",nullable = false)
    private String numeroCertificatMedicalValide;
    @Column(name = "numeroVisiteTechnique",nullable = false)
    private String numeroVisiteTechnique;
    @Column(name = "attestionCapPoidsLourd",nullable = false)
    private String attestionCapPoidsLourd;
    @Column(name = "attestationDeConduiteDefensive",nullable = false)
    private String attestationDeConduiteDefensive;

    @OneToMany(mappedBy = "chauffeur")
    private List<CertificatControl> certificatControl;

}
