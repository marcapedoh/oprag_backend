package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.AuthenticationLogAPI;
import oprag.project.gestionControleDAcces.dto.AuthenticationLogDAO;
import oprag.project.gestionControleDAcces.services.AuthenticationLogSerice;
import oprag.project.gestionControleDAcces.services.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationLogController implements AuthenticationLogAPI {
    private AuthenticationLogSerice authenticationLogSerice;

    @Autowired
    public AuthenticationLogController(AuthenticationLogSerice authenticationLogSerice) {
        this.authenticationLogSerice = authenticationLogSerice;
    }

    @Override
    public AuthenticationLogDAO save(AuthenticationLogDAO authenticationLogDAO) {
        return this.authenticationLogSerice.save(authenticationLogDAO);
    }

    @Override
    public List<AuthenticationLogDAO> findAllByUtilisateurId(Integer utilisateurId) {
        return this.authenticationLogSerice.findAllByUtilisateurId(utilisateurId);
    }

    @Override
    public List<AuthenticationLogDAO> findAll() {
        return this.authenticationLogSerice.findAll();
    }
}
