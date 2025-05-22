package oprag.project.gestionControleDAcces.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class Vehicule extends AbstractEntity {
    @Column(name = "numeroCarteGrise",nullable = false,unique = true)
    private String numeroCarteGrise;
    @Column(name = "typesVehicule",nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TypeVehicule> typeVehicules;
    @Column(name = "numeroAssurance",nullable = false)
    private String numeroAssurance;
    @Column(name = "numeroVisiteTechnique",nullable = false)
    private String numeroVisiteTechnique;
    @Column(name = "vgpRemorque",nullable = false)
    private String vgpRemorque;
    @Column(name = "active")
    private boolean active;
    @Column(name = "controleEquipementObligatoirel",nullable = false)
    private boolean controleEquipementObligatoirel;

    @OneToMany(mappedBy = "vehicule")
    private List<CertificatControl>  certificatControls;
}
