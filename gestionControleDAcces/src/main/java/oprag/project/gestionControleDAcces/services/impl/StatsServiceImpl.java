package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.models.CertificatControl;
import oprag.project.gestionControleDAcces.models.TypeVehicule;
import oprag.project.gestionControleDAcces.models.Vehicule;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {
    private CertificatControlRepository certificatControlRepository;

    @Autowired
    public StatsServiceImpl(CertificatControlRepository certificatControlRepository) {
        this.certificatControlRepository = certificatControlRepository;
    }

    @Override
    public Map<String, Object> getStatusStatsInit(LocalDate dateDebut, LocalDate dateFin) {
        List<Object[]> rows = certificatControlRepository.getStatusStatsInit(dateDebut, dateFin);
        List<String> categories = new ArrayList<>();
        List<Integer> conformes = new ArrayList<>();
        List<Integer> nonConformes = new ArrayList<>();

        for (Object[] row : rows) {
            categories.add(row[0].toString());
            conformes.add(((Number) row[1]).intValue());
            nonConformes.add(((Number) row[2]).intValue());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("conformes", conformes);
        result.put("nonConformes", nonConformes);
        return result;
    }

    @Override
    public Map<String, Object> getStatusStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        List<Object[]> rows = certificatControlRepository.getStatusStats(dateDebut, dateFin, inspectionId);
        List<String> categories = new ArrayList<>();
        List<Integer> conformes = new ArrayList<>();
        List<Integer> nonConformes = new ArrayList<>();

        for (Object[] row : rows) {
            categories.add(row[0].toString());
            conformes.add(((Number) row[1]).intValue());
            nonConformes.add(((Number) row[2]).intValue());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("conformes", conformes);
        result.put("nonConformes", nonConformes);
        return result;
    }

    @Override
    public Map<String, Object> getTrendStatsInit(LocalDate dateDebut, LocalDate dateFin) {
        List<Object[]> rows = certificatControlRepository.getTrendStatsInit(dateDebut, dateFin);
        List<String> categories = new ArrayList<>();
        List<Integer> inspections = new ArrayList<>();

        for (Object[] row : rows) {
            categories.add(row[0].toString());
            inspections.add(((Number) row[1]).intValue());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("inspections", inspections);
        return result;
    }

    @Override
    public Map<String, Object> getTrendStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        List<Object[]> rows = certificatControlRepository.getTrendStats(dateDebut, dateFin, inspectionId);
        List<String> categories = new ArrayList<>();
        List<Integer> inspections = new ArrayList<>();

        for (Object[] row : rows) {
            categories.add(row[0].toString());
            inspections.add(((Number) row[1]).intValue());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("inspections", inspections);
        return result;
    }

    @Override
    public Map<String, Object> getVehicleTypeStatsInit(LocalDate dateDebut, LocalDate dateFin) {
        return Map.of();
    }

    @Override
    public Map<String, Object> getVehicleTypeStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        // Récupère les stats depuis la BDD
//        List<Object[]> certificats = certificatControlRepository.getVehicleTypeStats(dateDebut, dateFin, inspectionId);



        return null;
    }

    @Override
    public List<Map<String, Object>> countRapportsByDayAndInspection(LocalDate dateDebut, LocalDate dateFin) {
        List<Object[]> rows = certificatControlRepository.countRapportsByDayAndInspection(dateDebut, dateFin);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("NomInspection",(String) row[0]);
            map.put("date", ((java.sql.Date) row[1]).toLocalDate());
            map.put("count", ((Number) row[2]).longValue());
            result.add(map);
        }

        return result;

    }




}
