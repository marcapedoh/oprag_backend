package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.VehiculeDAO;

import java.util.List;

public interface VehiculeService {
    VehiculeDAO save(VehiculeDAO vehiculeDAO);
    VehiculeDAO findById(Integer id);
    VehiculeDAO findByNumeroCarteGrise(String numeroCarteGrise);
    List<VehiculeDAO> findAll();
    void delete(Integer id);
}
