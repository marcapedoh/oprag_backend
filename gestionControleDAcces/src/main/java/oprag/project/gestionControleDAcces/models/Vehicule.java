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
    @Column(name = "numeroCarteGrise")
    private String numeroCarteGrise;
    @Column(name = "typesVehicule")
    @Enumerated(EnumType.STRING)
    private List<TypeVehicule> typeVehicules;
    @Column(name = "numeroAssurance")
    private String numeroAssurance;
    @Column(name = "numeroVisiteTechnique")
    private String numeroVisiteTechnique;
    @Column(name = "vgpRemorque")
    private String vgpRemorque;
    @Column(name = "active")
    private boolean active;
    @Column(name = "controleEquipementObligatoirel")
    private boolean controleEquipementObligatoirel;

    @OneToMany(mappedBy = "vehicule")
    private List<CertificatControl>  certificatControls;
}
