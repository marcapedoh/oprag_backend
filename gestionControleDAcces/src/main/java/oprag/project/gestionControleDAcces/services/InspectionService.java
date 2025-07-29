package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.InspectionDAO;

import java.util.List;

public interface InspectionService {
    InspectionDAO save(InspectionDAO inspectionDAO);
    List<InspectionDAO> findAll();
    InspectionDAO findById(Integer id);
    List<Long> pieChartStatsData();
    InspectionDAO findByNom(String nom);
    void delete(Integer id);
}
