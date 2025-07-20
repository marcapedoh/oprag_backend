package oprag.project.gestionControleDAcces.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.Inspection;
import oprag.project.gestionControleDAcces.models.TypeControl;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionDAO {
    private Integer id;
    private String code;
    private String nom;
    private String codeInspection;
    private boolean status;
    private TypeControl type;
    private Instant createdDate;
    private String logo;
    @JsonIgnore
    private List<UtilisateurDAO> inspecteurs;

    public static InspectionDAO fromEntity(Inspection inspection) {
        if(inspection==null){
            return null;
        }

        return InspectionDAO.builder()
                .id(inspection.getId())
                .nom(inspection.getNom())
                .status(inspection.isStatus())
                .codeInspection(inspection.getCodeInspection())
                .type(inspection.getType())
                .code(inspection.getCode())
                .createdDate(inspection.getDateCreation())
                .logo(inspection.getLogo())
                .build();
    }

    public static Inspection toEntity(InspectionDAO inspectionDAO) {
        if(inspectionDAO==null){
            return null;
        }

        Inspection inspection = new Inspection();
        inspection.setId(inspectionDAO.getId());
        inspection.setNom(inspectionDAO.getNom());
        inspection.setCodeInspection(inspectionDAO.getCodeInspection());
        inspection.setType(inspectionDAO.getType());
        inspection.setStatus(inspectionDAO.isStatus());
        inspection.setCode(inspectionDAO.getCode());
        inspection.setLogo(inspectionDAO.getLogo());
        return inspection;
    }
}
