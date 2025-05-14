package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;

import java.util.List;

public interface CertificatControlService {
    CertificatControlDAO save(CertificatControlDAO certificatControlDAO);
    CertificatControlDAO findById(Integer id);
    List<CertificatControlDAO> findAll();
    void delete(Integer id);
}
