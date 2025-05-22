package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class Badge extends AbstractEntity {
    @Column(name = "numero",nullable = false)
    private String numero;
    @Column(name = "validite",nullable = false)
    private String validite;
    @Column(name = "numeroParc",nullable = false)
    private String numeroParc;
    @Column(name = "active",nullable = false)
    private boolean active;
    @Column(name = "codeQrString",nullable = false)
    private String codeQrString;
    //Recuperer le logo de l'inspection dans l'objet inspection et le mettre sur le badge

    @OneToOne
    @JoinColumn(name = "certificatControlId")
    private CertificatControl certificatControl;

    @ManyToOne
    @JoinColumn(name = "InspecteurId")
    private Utilisateur inspecteur;
}
