package oprag.project.gestionControleDAcces.controller;


import oprag.project.gestionControleDAcces.controller.API.DemandeLicenceAPI;
import oprag.project.gestionControleDAcces.dto.DemandeLicenceDAO;
import oprag.project.gestionControleDAcces.services.DemandeLicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemandeLicenceController implements DemandeLicenceAPI {

    private DemandeLicenceService demandeLicenceService;

    @Autowired
    public DemandeLicenceController(DemandeLicenceService demandeLicenceService) {
        this.demandeLicenceService = demandeLicenceService;
    }

    @Override
    public DemandeLicenceDAO save(DemandeLicenceDAO demandeLicenceDAO) {
        return this.demandeLicenceService.save(demandeLicenceDAO);
    }

    @Override
    public Page<DemandeLicenceDAO> findAll(int page, int size) {
        return this.demandeLicenceService.findAll(page, size);
    }

    @Override
    public void delete(Integer id) {
        this.demandeLicenceService.delete(id);
    }
}
