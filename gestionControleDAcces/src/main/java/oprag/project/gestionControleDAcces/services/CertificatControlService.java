package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;

import java.util.List;
import java.util.Map;

public interface CertificatControlService {
    CertificatControlDAO save(CertificatControlDAO certificatControlDAO);
    CertificatControlDAO findById(Integer id);
    List<CertificatControlDAO> findAll();
    long numberOfCertificatControls();
    List<Map<String,Object>> getCertificatControlsStatsByInspection();
    List<CertificatControlDAO> findCertificatControlByUtilisateurId(Integer id);
    void delete(Integer id);
}
