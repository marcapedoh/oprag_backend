package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule,Integer> {
    Optional<Vehicule> findVehiculeByNumeroCarteGrise(String numeroCarteGrise);
}
