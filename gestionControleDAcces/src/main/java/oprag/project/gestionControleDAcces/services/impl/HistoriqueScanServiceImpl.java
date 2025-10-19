package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.DemandeLicenceDAO;
import oprag.project.gestionControleDAcces.dto.HistoriqueScanDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.DemandeLicence;
import oprag.project.gestionControleDAcces.models.HistoriqueScan;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.HistoriqueScanRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.HistoriqueScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoriqueScanServiceImpl implements HistoriqueScanService {
    private HistoriqueScanRepository historiqueScanRepository;
    private UtilisateurRepository  utilisateurRepository;

    @Autowired
    public HistoriqueScanServiceImpl(HistoriqueScanRepository historiqueScanRepository, UtilisateurRepository  utilisateurRepository) {
        this.historiqueScanRepository = historiqueScanRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public HistoriqueScanDAO save(HistoriqueScanDAO historiqueScanDAO) {
        Utilisateur utilisateur=this.utilisateurRepository.findById(historiqueScanDAO.getUtilisateur().getId()).orElse(null);
        if(utilisateur!=null){
            historiqueScanDAO.setDate(LocalDateTime.now());
            historiqueScanDAO.setUtilisateur(UtilisateurDAO.fromEntity(utilisateur));
            return HistoriqueScanDAO.fromEntity(historiqueScanRepository.save(HistoriqueScanDAO.toEntity(historiqueScanDAO)));
        }
        return null;
    }

    @Override
    public Page<HistoriqueScanDAO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").descending());
        Page<HistoriqueScan> historiqueScans = historiqueScanRepository.findAll(pageable);
        return historiqueScans.map(HistoriqueScanDAO::fromEntity);
    }
}
