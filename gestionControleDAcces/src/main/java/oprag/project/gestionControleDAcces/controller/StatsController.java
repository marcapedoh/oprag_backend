package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.StatsAPI;
import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
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
    public Object stats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        Map<String, Object> result = new HashMap<>();

        result.put("status", this.statsService.getStatusStats(dateDebut, dateFin, inspectionId));
        result.put("trend", this.statsService.getTrendStats(dateDebut, dateFin, inspectionId));
        result.put("vehicleTypes", this.statsService.getVehicleTypeStats(dateDebut, dateFin, inspectionId));
        result.put("trendMultiLine",this.statsService.countRapportsByDayAndInspection(dateDebut, dateFin, inspectionId));
        result.put("ConformeRate",(this.certificatControlRepository.countCertificatControlByAvisFavorable(dateDebut, dateFin, inspectionId)/this.certificatControlRepository.findAll().size())*100);
        result.put("totalRapport",this.certificatControlRepository.findAll().size());
        result.put("totalCard",this.badgeService.findAll().size());
        result.put("filteredData",this.certificatControlRepository.findCertificatControlByCreationDateBetweenAndUtilisateurInspectionId(dateDebut,dateFin, Math.toIntExact(inspectionId)).stream().map(CertificatControlDAO::fromEntity).collect(Collectors.toList()));
        result.put("totalCardActive",this.badgeService.findAll().stream().filter(BadgeDAO::isActive).toList().size());
        return result;
    }
}
