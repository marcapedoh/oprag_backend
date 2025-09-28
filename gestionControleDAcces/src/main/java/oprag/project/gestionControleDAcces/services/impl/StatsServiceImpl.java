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
        List<Object[]> rows = certificatControlRepository.getVehicleTypeStats(dateDebut, dateFin, null);

        Map<String, Long> countsMap = new HashMap<>();

        for (Object[] row : rows) {
            Object typeObj = row[0];   // Liste<TypeVehicule> ou TypeVehicule
            Long count = (Long) row[1];

            if (typeObj instanceof List<?> typeList) {
                // Si c’est une liste, on parcourt
                for (Object t : typeList) {
                    String typeName = (t instanceof Enum<?> e) ? e.name() : t.toString();
                    countsMap.merge(typeName, count, Long::sum);
                }
            } else {
                // Si c’est un seul élément
                String typeName = (typeObj instanceof Enum<?> e) ? e.name() : typeObj.toString();
                countsMap.merge(typeName, count, Long::sum);
            }
        }

        // Extraire proprement labels et séries
        List<String> labels = new ArrayList<>(countsMap.keySet());
        List<Long> series = labels.stream().map(countsMap::get).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("series", series);

        return result;
    }


    @Override
    public Map<String, Object> getVehicleTypeStats(LocalDate dateDebut, LocalDate dateFin, Long inspectionId) {
        List<Object[]> rows = certificatControlRepository.getVehicleTypeStats(dateDebut, dateFin, inspectionId);

        Map<String, Long> countsMap = new HashMap<>();

        for (Object[] row : rows) {
            Object typeObj = row[0];   // Liste<TypeVehicule> ou TypeVehicule
            Long count = (Long) row[1];

            if (typeObj instanceof List<?> typeList) {
                // Si c’est une liste, on parcourt
                for (Object t : typeList) {
                    String typeName = (t instanceof Enum<?> e) ? e.name() : t.toString();
                    countsMap.merge(typeName, count, Long::sum);
                }
            } else {
                // Si c’est un seul élément
                String typeName = (typeObj instanceof Enum<?> e) ? e.name() : typeObj.toString();
                countsMap.merge(typeName, count, Long::sum);
            }
        }

        // Extraire proprement labels et séries
        List<String> labels = new ArrayList<>(countsMap.keySet());
        List<Long> series = labels.stream().map(countsMap::get).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("series", series);

        return result;
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
