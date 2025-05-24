package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.BadgeAPI;
import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.services.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BadgeController implements BadgeAPI {
    private BadgeService badgeService;

    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }
    @Override
    public BadgeDAO save(BadgeDAO badgeDAO) {
        return this.badgeService.save(badgeDAO);
    }

    @Override
    public BadgeDAO findByNumero(String numero) {
        return this.badgeService.findByNumero(numero);
    }

    @Override
    public List<BadgeDAO> findAll() {
        return this.badgeService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.badgeService.delete(id);
    }
}
