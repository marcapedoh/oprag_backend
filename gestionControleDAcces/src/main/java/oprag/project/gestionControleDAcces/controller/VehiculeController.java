package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.VehiculeAPI;
import oprag.project.gestionControleDAcces.dto.VehiculeDAO;
import oprag.project.gestionControleDAcces.services.VehiculeService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehiculeController implements VehiculeAPI {
    private VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @Override
    public VehiculeDAO save(VehiculeDAO vehiculeDAO) {
        return this.vehiculeService.save(vehiculeDAO);
    }

    @Override
    public VehiculeDAO findById(Integer id) {
        return this.vehiculeService.findById(id);
    }

    @Override
    public VehiculeDAO findByNumeroCarteGrise(String numeroCarteGrise) {
        return this.vehiculeService.findByNumeroCarteGrise(numeroCarteGrise);
    }

    @Override
    public List<VehiculeDAO> findAll() {
        return this.vehiculeService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.vehiculeService.delete(id);
    }
}
