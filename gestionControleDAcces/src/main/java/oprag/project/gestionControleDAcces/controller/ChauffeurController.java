package oprag.project.gestionControleDAcces.controller;

import lombok.RequiredArgsConstructor;
import oprag.project.gestionControleDAcces.auth.AuthenticationResponse;
import oprag.project.gestionControleDAcces.auth.AuthenticationService;
import oprag.project.gestionControleDAcces.controller.API.ChauffeurAPI;
import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.services.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChauffeurController implements ChauffeurAPI {

    private  ChauffeurService chauffeurService;
    private  AuthenticationService authenticationService;

    @Autowired
    public ChauffeurController(ChauffeurService chauffeurService, AuthenticationService authenticationService) {
        this.chauffeurService = chauffeurService;
        this.authenticationService = authenticationService;
    }

    @Override
    public ChauffeurDAO register(ChauffeurDAO chauffeurDAO) {
        return this.authenticationService.registerChauffeur(chauffeurDAO);
    }

    @Override
    public ChauffeurDAO findById(Integer id) {
        return this.chauffeurService.findById(id);
    }

    @Override
    public ChauffeurDAO findByUserName(String userName) {
        return this.chauffeurService.findByUserName(userName);
    }

    @Override
    public AuthenticationResponse authenticate(String userName, String motDePasse) {
        return this.authenticationService.authenticateChauffeur(userName, motDePasse);
    }

    @Override
    public List<ChauffeurDAO> findAll() {
        return this.chauffeurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.chauffeurService.delete(id);
    }
}
