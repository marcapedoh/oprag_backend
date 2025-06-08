package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportData {
    private CertificatControlDAO certificatControl;
    private LocalDate dateCertificat;
    private UtilisateurDAO utilisateur;
    private InspectionDAO inspection;
    private String essaisConcatenes;
}
