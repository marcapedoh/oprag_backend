package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.InspectionMontantAPI;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import oprag.project.gestionControleDAcces.services.InspectionMontantService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class InspectionMontantController implements InspectionMontantAPI {

    private  InspectionMontantService inspectionMontantService;

    public InspectionMontantController(InspectionMontantService inspectionMontantService) {
        this.inspectionMontantService = inspectionMontantService;
    }

    @Override
    public InspectionMontantDAO save(InspectionMontantDAO inspectionMontantDAO) {
        return this.inspectionMontantService.save(inspectionMontantDAO);
    }

    @Override
    public List<InspectionMontantDAO> findAll() {
        return inspectionMontantService.findAll();
    }

    @Override
    public InspectionMontantDAO findById(Integer id) {
        return this.inspectionMontantService.findById(id);
    }

    @Override
    public void delete(Integer id) {
        this.inspectionMontantService.delete(id);
    }
}
