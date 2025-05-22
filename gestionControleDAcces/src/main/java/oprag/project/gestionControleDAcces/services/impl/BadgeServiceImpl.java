package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.repository.BadgeRepository;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.BadgeService;
import oprag.project.gestionControleDAcces.services.QRCodeUtil;
import oprag.project.gestionControleDAcces.validators.BadgeValidator;
import oprag.project.gestionControleDAcces.validators.InspectionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final CertificatControlRepository certificatControlRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CertificatControlRepository certificateControlRepository;
    @Autowired
    public BadgeServiceImpl(BadgeRepository badgeRepository, CertificatControlRepository certificatControlRepository, UtilisateurRepository utilisateurRepository, CertificatControlRepository certificateControlRepository) {
        this.badgeRepository = badgeRepository;
        this.certificatControlRepository = certificatControlRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.certificateControlRepository = certificateControlRepository;
    }




    @Override
    public BadgeDAO save(BadgeDAO badgeDAO) {
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        var inspecteur=this.utilisateurRepository.findById(badgeDAO.getInspecteur().getId()).orElseThrow(()-> new EntityNotFoundException("L'inspecteur n'est pas trouvé dans la base de donnée"));
        var certificatControl=this.certificateControlRepository.findById(badgeDAO.getCertificatControl().getId()).orElseThrow(()-> new EntityNotFoundException("Aucun certficatControl pour ce id"));

        badgeDAO.setNumero(
                inspecteur.getInspection().getNom().substring(0,4)
                +"-"+certificatControl.getVehicule().getTypeVehicules().get(0).toString().substring(0,4)
                +"-"+LocalDate.now()+"-"+randomInt);
        if(badgeRepository.findBadgeByNumero(badgeDAO.getNumero()).isPresent()){
            throw new InvalidOperationException("ce badge existe déjà dans la base de donnée", ErrorCodes.BADGE_ALREADY_EXIST);
        }
        String qrContent = "DateCreation:" + LocalDate.now() + ";Numero:" + badgeDAO.getNumero();

        try {
            BufferedImage qrImage = QRCodeUtil.generateQRCodeImage(qrContent, 80, 80);


            String downloadPath = getDownloadFolderPath();
            String fileName = "badge_" + badgeDAO.getNumero() + ".png";
            File outputFile = new File(downloadPath, fileName);

            ImageIO.write(qrImage, "png", outputFile);

            badgeDAO.setCodeQrString(outputFile.getAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du QR Code", e);
        }

        List<String> errors= BadgeValidator.validate(badgeDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le badge que vous passez n'est pas valide",ErrorCodes.BADGE_NOT_VALID,errors);
        }

        badgeDAO.setInspecteur(UtilisateurDAO.fromEntity(inspecteur));
        badgeDAO.setCertificatControl(CertificatControlDAO.fromEntity(certificatControl));

        var badge = BadgeDAO.fromEntity(
                badgeRepository.save(
                        BadgeDAO.toEntity(badgeDAO)));

        certificatControl.setBadge(BadgeDAO.toEntity(badge));
        certificateControlRepository.save(certificatControl); // mettez à jour explicitement

        return badge;
    }

    private String getDownloadFolderPath() {
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + "Downloads";
    }

    @Override
    public BadgeDAO findByNumero(String numero) {
        return this.badgeRepository.findBadgeByNumero(numero)
                .map(BadgeDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("Aucun badge n'est trouvé pour ce numero", ErrorCodes.BADGE_NOT_FOUND));
    }

    @Override
    public List<BadgeDAO> findAll() {
        return this.badgeRepository.findAll().stream()
                .map(BadgeDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id != null;
        if(this.badgeRepository.existsById(id)){
            this.badgeRepository.findById(id).ifPresent(badge -> {
                badge.setActive(false);
                this.badgeRepository.save(badge);
            });
        }
    }
}
