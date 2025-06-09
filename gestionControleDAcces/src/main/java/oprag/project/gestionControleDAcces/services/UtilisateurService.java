package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.Utilisateur;

import java.time.Instant;
import java.util.List;

public interface UtilisateurService {

    UtilisateurDAO findById(Integer id);
    UtilisateurDAO findByEmail(String email);
    List<UtilisateurDAO> findAll();
    List<UtilisateurDAO> findAllByInspectionNom(String nomInspection);
    void changeState(Integer id);
    Instant lastOperationDate(Integer id);
    void delete(Integer id);
}
