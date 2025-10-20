package oprag.project.gestionControleDAcces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.DemandeLicence;
import oprag.project.gestionControleDAcces.models.Inspection;
import oprag.project.gestionControleDAcces.models.Licence;
import oprag.project.gestionControleDAcces.models.LicenceStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenceDAO {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private LicenceStatus status;
    private Integer graceDays;
    private String numerolicence;
    private String pays;
    private InspectionDAO partenaire;
    private Boolean isPro;
    private Boolean isFree;
    @JsonIgnore
    private List<DemandeLicence> demandeLicences;

    public static LicenceDAO fromEntity(Licence licence) {
        if(licence == null) return null;

        return LicenceDAO.builder()
                .id(licence.getId())
                .status(licence.getStatus())
                .graceDays(licence.getGraceDays())
                .numerolicence(licence.getNumerolicence())
                .pays(licence.getPays())
                .isPro(licence.getIsPro())
                .isFree(licence.getIsFree())
                .partenaire(InspectionDAO.fromEntity(licence.getPartenaire()))
                .build();
    }

    public static Licence toEntity(LicenceDAO licenceDAO) {
        if(licenceDAO == null) return null;

        Licence licence = new Licence();
        licence.setId(licenceDAO.getId());
        licence.setStatus(licenceDAO.getStatus());
        licence.setGraceDays(licenceDAO.getGraceDays());
        licence.setNumerolicence(licenceDAO.getNumerolicence());
        licence.setPays(licenceDAO.getPays());
        licence.setIsFree(licenceDAO.getIsFree());
        licence.setIsPro(licenceDAO.getIsPro());
        licence.setPartenaire(InspectionDAO.toEntity(licenceDAO.getPartenaire()));
        return licence;
    }


}
