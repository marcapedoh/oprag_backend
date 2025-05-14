package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findUtilisateurByUserName(String userName);
    Optional<Utilisateur> findUtilisateurByEmail(String email);
    List<Utilisateur> findUtilisateurByInspectionId(Integer id);

    Optional<Utilisateur> findUtilisateurByRole(UserRole role);
}
