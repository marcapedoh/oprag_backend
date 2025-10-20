package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.LicenceAPI;
import oprag.project.gestionControleDAcces.dto.LicenceDAO;
import oprag.project.gestionControleDAcces.services.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenceController implements LicenceAPI {

    private LicenceService licenceService;

    @Autowired
    public LicenceController(LicenceService licenceService) {
        this.licenceService = licenceService;
    }

    @Override
    public LicenceDAO save(LicenceDAO licenceDAO) {
        return this.licenceService.save(licenceDAO);
    }

    @Override
    public Page<LicenceDAO> getLicences(int page, int size) {
        return this.licenceService.getLicences(page, size);
    }
}
