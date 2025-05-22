package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDAO findById(Integer id);
    UtilisateurDAO findByEmail(String email);
    List<UtilisateurDAO> findAll();
    void changeState(Integer id);
    void delete(Integer id);
}
