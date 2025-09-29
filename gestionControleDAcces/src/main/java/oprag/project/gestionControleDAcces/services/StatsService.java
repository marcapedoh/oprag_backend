package oprag.project.gestionControleDAcces.services;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatsService {
    public Map<String, Object> getStatusStatsInit(LocalDate dateDebut, LocalDate dateFin);
    public Map<String, Object> getStatusStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId);
    public Map<String, Object> getTrendStatsInit(LocalDate dateDebut, LocalDate dateFin);
    public Map<String, Object> getTrendStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId);

    public Map<String, Object> getVehicleTypeStatsInit(LocalDate dateDebut, LocalDate dateFin);
    public Map<String, Object> getVehicleTypeStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId);

    List<Map<String, Object>> countRapportsByDayAndInspection(LocalDate dateDebut,LocalDate dateFin);
}
