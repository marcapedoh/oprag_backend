package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.HistoriqueScanDAO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoriqueScanService {
    HistoriqueScanDAO save(HistoriqueScanDAO historiqueScanDAO);
    Page<HistoriqueScanDAO> findAll(int page, int size);

}
