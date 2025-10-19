package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.DemandeLicenceDAO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DemandeLicenceService {
    DemandeLicenceDAO save(DemandeLicenceDAO demandeLicenceDAO);
    Page<DemandeLicenceDAO> findAll(int page, int size);
    void delete(Integer id);
}
