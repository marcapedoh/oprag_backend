package oprag.project.gestionControleDAcces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.TypeVehicule;
import oprag.project.gestionControleDAcces.models.Vehicule;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculeDAO {
    private Integer id;
    private String numeroCarteGrise;
    @Enumerated(EnumType.STRING)
    private List<TypeVehicule> typeVehicules;
    private String numeroAssurance;
    private String numeroVisiteTechnique;
    private String vgpRemorque;
    private boolean controleEquipementObligatoirel;


    @JsonIgnore
    private List<CertificatControlDAO>  certificatControls;

    public static VehiculeDAO fromEntity(Vehicule vehicule) {
        if(vehicule==null){
            return null;
        }

        return VehiculeDAO.builder()
                .id(vehicule.getId())
                .numeroCarteGrise(vehicule.getNumeroCarteGrise())
                .typeVehicules(vehicule.getTypeVehicules())
                .numeroAssurance(vehicule.getNumeroAssurance())
                .numeroVisiteTechnique(vehicule.getNumeroVisiteTechnique())
                .vgpRemorque(vehicule.getVgpRemorque())
                .controleEquipementObligatoirel(vehicule.isControleEquipementObligatoirel())
                .build();
    }

    public static Vehicule toEntity(VehiculeDAO vehiculeDAO) {
        if(vehiculeDAO==null){
            return null;
        }

        Vehicule vehicule = new Vehicule();
        vehicule.setId(vehiculeDAO.getId());
        vehicule.setNumeroCarteGrise(vehiculeDAO.getNumeroCarteGrise());
        vehicule.setTypeVehicules(vehiculeDAO.getTypeVehicules());
        vehicule.setNumeroAssurance(vehiculeDAO.getNumeroAssurance());
        vehicule.setNumeroVisiteTechnique(vehiculeDAO.getNumeroVisiteTechnique());
        vehicule.setVgpRemorque(vehiculeDAO.getVgpRemorque());
        vehicule.setControleEquipementObligatoirel(vehiculeDAO.isControleEquipementObligatoirel());
        return vehicule;
    }
}
