package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.DemandeLicenceDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.models.DemandeLicence;
import oprag.project.gestionControleDAcces.models.StatutDemande;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.DemandeLicenceRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.DemandeLicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DemandeLicenceServiceImpl implements DemandeLicenceService {
    private DemandeLicenceRepository demandeLicenceRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public DemandeLicenceServiceImpl(DemandeLicenceRepository demandeLicenceRepository, UtilisateurRepository utilisateurRepository) {
        this.demandeLicenceRepository = demandeLicenceRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public DemandeLicenceDAO save(DemandeLicenceDAO demandeLicenceDAO) {
        Utilisateur utilisateur=this.utilisateurRepository.findById(demandeLicenceDAO.getUtilisateur().getId()).orElse(null);
        if(utilisateur!=null){
            demandeLicenceDAO.setUtilisateur(UtilisateurDAO.fromEntity(utilisateur));
            demandeLicenceDAO.setDateSoumission(LocalDate.now());
            return DemandeLicenceDAO.fromEntity(demandeLicenceRepository.save(DemandeLicenceDAO.toEntity(demandeLicenceDAO)));
        }
        return null;
    }

    @Override
    public Page<DemandeLicenceDAO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").descending());
        Page<DemandeLicence> demandesPage = demandeLicenceRepository.findAll(pageable);
        return demandesPage.map(DemandeLicenceDAO::fromEntity);
    }

    @Override
    public void delete(Integer id) {
        if (demandeLicenceRepository.findById(id)
                .map(demandeLicence -> demandeLicence.getStatutDemande() == StatutDemande.APPROUVEE)
                .orElse(false)) {
            throw new InvalidOperationException("Vous ne pouvez pas supprimé une demandé qui a déjà été approuvé pour des raison de traçabilité");
        }else {
            demandeLicenceRepository.deleteById(id);
        }

    }
}
