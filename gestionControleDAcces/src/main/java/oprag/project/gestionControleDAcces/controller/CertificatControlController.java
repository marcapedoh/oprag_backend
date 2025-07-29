package oprag.project.gestionControleDAcces.controller;

import oprag.project.gestionControleDAcces.controller.API.CertificatControlAPI;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.services.CertificatControlService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CertificatControlController implements CertificatControlAPI {

    private CertificatControlService  certificatControlService;

    public CertificatControlController(CertificatControlService certificatControlService) {
        this.certificatControlService = certificatControlService;
    }

    @Override
    public CertificatControlDAO save(CertificatControlDAO certificatControlDAO) {
        return this.certificatControlService.save(certificatControlDAO);
    }

    @Override
    public CertificatControlDAO findById(Integer id) {
        return this.certificatControlService.findById(id);
    }

    @Override
    public long numberOfCertificatControls() {
        return this.certificatControlService.numberOfCertificatControls();
    }

    @Override
    public List<Map<String, Object>> getCertificatControlsStatsByInspection() {
        return this.certificatControlService.getCertificatControlsStatsByInspection();
    }

    @Override
    public List<CertificatControlDAO> findAll() {
        return this.certificatControlService.findAll();
    }

    @Override
    public List<CertificatControlDAO> findCertificatControlByUtilisateurId(Integer id) {
        return this.certificatControlService.findCertificatControlByUtilisateurId(id);
    }

    @Override
    public void delete(Integer id) {
        this.certificatControlService.delete(id);
    }
}
