package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.LicenceDAO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LicenceService {
    LicenceDAO save(LicenceDAO licenceDAO);
    Page<LicenceDAO> getLicences(int page, int size);
}
