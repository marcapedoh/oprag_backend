package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.UtilisateurAPI;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.services.UtilisateurService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UtilisateurController implements UtilisateurAPI {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDAO findById(Integer id) {
        return this.utilisateurService.findById(id);
    }

    @Override
    public UtilisateurDAO findByEmail(String email) {
        return this.utilisateurService.findByEmail(email);
    }

    @Override
    public List<UtilisateurDAO> findAll() {
        return this.utilisateurService.findAll();
    }

    @Override
    public Instant lastOperationDate(Integer id) {
        return this.utilisateurService.lastOperationDate(id);
    }

    @Override
    public List<UtilisateurDAO> findAllByInspectionNom(String nomInspection) {
        return this.utilisateurService.findAllByInspectionNom(nomInspection);
    }

    @Override
    public void delete(Integer id) {
        this.utilisateurService.delete(id);
    }

    @Override
    public void changeState(Integer id) {
        this.utilisateurService.changeState(id);
    }
}
