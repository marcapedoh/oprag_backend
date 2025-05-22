package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChauffeurRepository extends JpaRepository<Chauffeur,Integer> {
    Optional<Chauffeur> findByNom(String nom);
    Optional<Chauffeur> findChauffeurByUserName(String userName);
    Optional<Chauffeur> findChauffeurByUserNameAndMotDePasse(String username, String password);
}
