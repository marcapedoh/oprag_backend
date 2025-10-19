package oprag.project.gestionControleDAcces.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.HistoriqueScan;
import oprag.project.gestionControleDAcces.models.Utilisateur;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoriqueScanDAO {
    private Integer id;
    private UtilisateurDAO utilisateur;
    private LocalDateTime date;

    public static HistoriqueScanDAO fromEntity(HistoriqueScan historiqueScan){
        if(historiqueScan == null){
            return null;
        }
        return HistoriqueScanDAO.builder()
                .id(historiqueScan.getId())
                .utilisateur(UtilisateurDAO.fromEntity(historiqueScan.getUtilisateur()))
                .date(historiqueScan.getDate())
                .build();
    }

    public static HistoriqueScan toEntity(HistoriqueScanDAO historiqueScanDAO){
        if(historiqueScanDAO == null){
            return null;
        }
        HistoriqueScan historiqueScan = new HistoriqueScan();
        historiqueScan.setId(historiqueScanDAO.getId());
        historiqueScan.setDate(historiqueScanDAO.getDate());
        historiqueScan.setUtilisateur(UtilisateurDAO.toEntity(historiqueScanDAO.getUtilisateur()));
        return historiqueScan;
    }
}
