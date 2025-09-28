package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.StatsAPI;
import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.models.CertificatControl;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.services.BadgeService;
import oprag.project.gestionControleDAcces.services.StatsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class StatsController implements StatsAPI {
    private final StatsService statsService;
    private final CertificatControlRepository certificatControlRepository;
    private final BadgeService badgeService;


    public StatsController(BadgeService badgeService, CertificatControlRepository certificatControlRepository, StatsService statsService) {
        this.badgeService = badgeService;
        this.certificatControlRepository = certificatControlRepository;
        this.statsService = statsService;
    }


    @Override
    public Object statsWithoutInspection(LocalDate dateDebut, LocalDate dateFin) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", this.statsService.getStatusStatsInit(dateDebut, dateFin));
        result.put("trend", this.statsService.getTrendStatsInit(dateDebut, dateFin));
        result.put("vehicleTypes", this.statsService.getVehicleTypeStatsInit(dateDebut, dateFin));
        result.put("trendMultiLine",this.statsService.countRapportsByDayAndInspection(dateDebut, dateFin));
        result.put("InspectionPerSocetite",this.certificatControlRepository.countVehiculesBySociete());
        result.put("ConformeRate",this.certificatControlRepository.countCertificatControlByAvisFavorableInit(dateDebut, dateFin));
        result.put("totalRapport",this.certificatControlRepository.findAllByCreationDateBetween(dateDebut,dateFin).stream().filter(c-> !c.isDeleted()).map(CertificatControlDAO::fromEntity).toList().size());
        result.put("totalCard",this.badgeService.findAllCertificatCreationDate(dateDebut,dateFin).size());
        result.put("filteredData",this.certificatControlRepository.findCertificatControlByCreationDateBetween(dateDebut,dateFin).stream().filter(c -> !c.isDeleted()).map(CertificatControlDAO::fromEntity).toList());
        result.put("totalCardActive",this.badgeService.findAllCertificatCreationDate(dateDebut,dateFin).stream().filter(BadgeDAO::isActive).toList().size());

        return result;
    }


    @Override
    public Object stats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", this.statsService.getStatusStats(dateDebut, dateFin, inspectionId));
        result.put("trend", this.statsService.getTrendStats(dateDebut, dateFin, inspectionId));
        result.put("vehicleTypes", this.statsService.getVehicleTypeStats(dateDebut, dateFin, inspectionId));
        result.put("trendMultiLine",this.statsService.countRapportsByDayAndInspection(dateDebut, dateFin));
        result.put("InspectionPerSocetite",this.certificatControlRepository.countVehiculesBySociete());
        result.put("ConformeRate",this.certificatControlRepository.countCertificatControlByAvisFavorable(dateDebut, dateFin, inspectionId));
        result.put("totalRapport",this.certificatControlRepository.findAllByCreationDateBetweenAndUtilisateurInspectionId(dateDebut,dateFin,Math.toIntExact(inspectionId)).stream().filter(c->!c.isDeleted()).map(CertificatControlDAO::fromEntity).toList().size());
        result.put("totalCard",this.badgeService.findAllCertificatCreationDateAndInspectionId(dateDebut,dateFin,Math.toIntExact(inspectionId)).size());
        result.put("filteredData",this.certificatControlRepository.findCertificatControlByCreationDateBetweenAndUtilisateurInspectionId(dateDebut,dateFin, Math.toIntExact(inspectionId)).stream().map(CertificatControlDAO::fromEntity).collect(Collectors.toList()));
        result.put("totalCardActive",this.badgeService.findAllCertificatCreationDateAndInspectionId(dateDebut,dateFin,Math.toIntExact(inspectionId)).stream().filter(BadgeDAO::isActive).toList().size());

        return result;
    }
}
