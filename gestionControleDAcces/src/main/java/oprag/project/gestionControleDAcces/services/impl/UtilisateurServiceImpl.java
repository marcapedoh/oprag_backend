package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.models.Badge;
import oprag.project.gestionControleDAcces.models.CertificatControl;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.BadgeRepository;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.UtilisateurService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final CertificatControlRepository certificatControlRepository;
    private final BadgeRepository badgeRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,CertificatControlRepository certificatControlRepository,BadgeRepository badgeRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.certificatControlRepository = certificatControlRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public UtilisateurDAO findById(Integer id) {
        assert id != null;
        return this.utilisateurRepository.findById(id).map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Utilisateur n'existe pas pour cet id", ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public UtilisateurDAO findByEmail(String email) {
        assert email != null;
        return this.utilisateurRepository.findUtilisateurByEmail(email).map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Utilisateur n'existe pas pour cet email",ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public List<UtilisateurDAO> findAll() {
        return this.utilisateurRepository.findAll().stream()
                .map(UtilisateurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UtilisateurDAO> findAllByInspectionNom(String nomInspection) {
        return this.utilisateurRepository.findUtilisateurByInspectionNom(nomInspection).stream()
                .map(UtilisateurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id != null;
        if(this.certificatControlRepository.findCertificatControlByUtilisateurId(id).isEmpty()){
            this.utilisateurRepository.deleteById(id);
        }else{
            throw new InvalidOperationException("vous ne pouvez pas supprimé un utilisateur qui est déjà liés à des opérations dans la base de données",ErrorCodes.UTILISATEUR_EN_COURS_D_UTILSATION);
        }
    }

    @Override
    public void changeState(Integer id) {
        assert id != null;
        if(this.certificatControlRepository.findCertificatControlByUtilisateurId(id).isEmpty() && this.utilisateurRepository.findById(id).isPresent()){
            var utilisateur=this.utilisateurRepository.findById(id).get();
            utilisateur.setActive(false);
            this.utilisateurRepository.save(utilisateur);
        }else{
            throw new InvalidOperationException("Utilisateur est opération sur les objets de la base de donnée on peut donc pas le désactivé",ErrorCodes.UTILISATEUR_EN_COURS_D_UTILSATION);
        }
    }

    @Override
    public Instant lastOperationDate(Integer id) {
        var certificationControl=this.certificatControlRepository.findTopByUtilisateurIdOrderByCreationDateDesc(id);
        var badge=this.badgeRepository.findTopByInspecteurIdOrderByDateCreationDesc(id);
        Instant dateCertificat = certificationControl.map(CertificatControl::getCreationDate).orElse(null);
        Instant dateBadge = badge.map(Badge::getDateCreation).orElse(null);

        if (dateCertificat == null && dateBadge == null) {
            return null;
        } else if (dateCertificat == null) {
            return dateBadge;
        } else if (dateBadge == null) {
            return dateCertificat;
        } else {
            return dateCertificat.isAfter(dateBadge) ? dateCertificat : dateBadge;
        }
    }
}
