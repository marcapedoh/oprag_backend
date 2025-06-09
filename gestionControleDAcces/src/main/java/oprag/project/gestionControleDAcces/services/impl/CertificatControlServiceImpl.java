package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.repository.BadgeRepository;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.services.CertificatControlService;
import oprag.project.gestionControleDAcces.validators.CertificatControlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificatControlServiceImpl implements CertificatControlService {
    private final CertificatControlRepository  certificatControlRepository;
    private final BadgeRepository badgeRepository;
    @Autowired
    public CertificatControlServiceImpl(CertificatControlRepository certificatControlRepository,BadgeRepository badgeRepository) {
        this.certificatControlRepository = certificatControlRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public CertificatControlDAO save(CertificatControlDAO certificatControlDAO) {

        List<String> errors= CertificatControlValidator.validate(certificatControlDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le certificat que vous passez n'est pas valide",ErrorCodes.CERTFICAT_NOT_VALID,errors);
        }
//        var certificatControl=CertificatControlDAO.fromEntity(
//                certificatControlRepository.save(
//                        CertificatControlDAO.toEntity(certificatControlDAO)
//                )
//        );
//        BadgeDAO badgeDAO=BadgeDAO.builder()
//                .numero("numero-"+ UUID.randomUUID().toString())
//                .validite(certificatControlDAO.getValidite().toString())
//                .numeroParc("parc-numero"+ UUID.randomUUID().toString())
//                .codeQrString("QRCODEString-Path")
//                .active(true)
//                .certificatControl(certificatControl)
//                .inspecteur(certificatControl.getUtilisateur())
//                .build();
//        var badgeObj=badgeRepository.save(BadgeDAO.toEntity(badgeDAO));
//        certificatControl.setBadge(BadgeDAO.fromEntity(badgeObj));
//        certificatControl.setDeleted(false);
        certificatControlDAO.setCreationDate(Instant.now());
        return CertificatControlDAO.fromEntity(
                certificatControlRepository.save(
                        CertificatControlDAO.toEntity(certificatControlDAO)
                )
        );
    }

    @Override
    public CertificatControlDAO findById(Integer id) {
        assert id!=null;
        return this.certificatControlRepository.findById(id)
                .map(CertificatControlDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucun certificat de control n'est trouvé pour ce id",ErrorCodes.CERTIFICAT_NOT_FOUND));
    }

    @Override
    public List<CertificatControlDAO> findAll() {
        return this.certificatControlRepository.findAll().stream()
                .map(CertificatControlDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CertificatControlDAO> findCertificatControlByUtilisateurId(Integer id) {
        assert id!=null;
        return this.certificatControlRepository.findCertificatControlByUtilisateurId(id).stream()
                .map(CertificatControlDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id!=null;
        if(this.certificatControlRepository.existsById(id)){
            var certificatControlDAO=this.certificatControlRepository.findById(id).map(CertificatControlDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Aucun certificat de control n'est trouvé pour ce id",ErrorCodes.CERTIFICAT_NOT_FOUND));
            certificatControlDAO.setDeleted(true);
            this.certificatControlRepository.save(CertificatControlDAO.toEntity(certificatControlDAO));
        }
    }
}
