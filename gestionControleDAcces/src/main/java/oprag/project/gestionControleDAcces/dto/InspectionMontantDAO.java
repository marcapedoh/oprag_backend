package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.Inspection;
import oprag.project.gestionControleDAcces.models.InspectionMontant;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionMontantDAO {
    private Integer id;
    private Double montant;
    private Instant createdDate;
    public static InspectionMontantDAO fromEntity(InspectionMontant inspectionMontant) {
        if(inspectionMontant==null){
            return null;
        }

        return InspectionMontantDAO.builder()
                .id(inspectionMontant.getId())
                .montant(inspectionMontant.getMontant())
                .createdDate(inspectionMontant.getDateCreation())
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
