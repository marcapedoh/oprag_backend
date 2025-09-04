package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectionCardData {
    private String numeroRapport;
    private String validiteDate;
    private String numeroImmatriculation;
    private String nomSociete;
    private String codeInspecteur;
    private String partenaireTechnique;
    private String qrCodeString;
}
