package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;

import java.util.List;

public interface InspectionMontantService {
    InspectionMontantDAO save(InspectionMontantDAO inspectionMontantDAO);
    InspectionMontantDAO findById(Integer id);
    List<InspectionMontantDAO> findAll();
    void delete(Integer id);
}
