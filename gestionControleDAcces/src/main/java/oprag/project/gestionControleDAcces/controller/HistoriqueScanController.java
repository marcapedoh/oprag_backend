package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.HistoriqueScanAPI;
import oprag.project.gestionControleDAcces.dto.HistoriqueScanDAO;
import oprag.project.gestionControleDAcces.services.HistoriqueScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoriqueScanController implements HistoriqueScanAPI {

    private HistoriqueScanService historiqueScanService;

    @Autowired
    public HistoriqueScanController(HistoriqueScanService historiqueScanService) {
        this.historiqueScanService = historiqueScanService;
    }

    @Override
    public HistoriqueScanDAO save(HistoriqueScanDAO historiqueScanDAO) {
        return this.historiqueScanService.save(historiqueScanDAO);
    }

    @Override
    public Page<HistoriqueScanDAO> findAll(int page, int size) {
        return this.historiqueScanService.findAll(page, size);
    }
}
