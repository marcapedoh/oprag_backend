package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.InspectionAPI;
import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.services.InspectionService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InspectionController implements InspectionAPI {
    private InspectionService inspectionService;
    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @Override
    public InspectionDAO save(InspectionDAO inspectionDAO) {
        return this.inspectionService.save(inspectionDAO);
    }

    @Override
    public List<InspectionDAO> findAll() {
        return this.inspectionService.findAll();
    }

    @Override
    public InspectionDAO findById(Integer id) {
        return this.inspectionService.findById(id);
    }

    @Override
    public InspectionDAO findByNom(String nom) {
        return this.inspectionService.findByNom(nom);
    }

    @Override
    public void delete(Integer id) {
        this.inspectionService.delete(id);
    }
}
