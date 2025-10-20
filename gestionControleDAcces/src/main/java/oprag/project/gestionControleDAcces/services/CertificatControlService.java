package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CertificatControlService {
    CertificatControlDAO save(CertificatControlDAO certificatControlDAO);
    CertificatControlDAO findById(Integer id);
    Page<CertificatControlDAO> findAll(int page, int size);
    long numberOfCertificatControls();
    Double certificatControlsAmount();
    List<Map<String,Object>> getCertificatControlsStatsByInspection();
    List<CertificatControlDAO> findCertificatControlByUtilisateurId(Integer id);
    void delete(Integer id);
}
