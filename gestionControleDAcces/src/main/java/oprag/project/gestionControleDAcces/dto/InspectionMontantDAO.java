package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.Inspection;
import oprag.project.gestionControleDAcces.models.InspectionMontant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionMontantDAO {
    private Integer id;
    private Double montant;

    public static InspectionMontantDAO fromEntity(InspectionMontant inspectionMontant) {
        if(inspectionMontant==null){
            return null;
        }

        return InspectionMontantDAO.builder()
                .id(inspectionMontant.getId())
                .montant(inspectionMontant.getMontant())
                .build();
    }

    public static InspectionMontant toEntity(InspectionMontantDAO inspectionMontantDAO) {
        if(inspectionMontantDAO==null){
            return null;
        }
        InspectionMontant inspectionMontant = new InspectionMontant();
        inspectionMontant.setId(inspectionMontantDAO.getId());
        inspectionMontant.setMontant(inspectionMontantDAO.getMontant());
        return inspectionMontant;
    }
}
