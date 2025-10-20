package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.HistoriqueScanDAO;
import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.dto.LicenceDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.*;
import oprag.project.gestionControleDAcces.repository.InspectionRepository;
import oprag.project.gestionControleDAcces.repository.LicenceRepository;
import oprag.project.gestionControleDAcces.services.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Service
public class LicenceServiceImpl implements LicenceService {
    private LicenceRepository licenceRepository;
    private InspectionRepository inspectionRepository;

    @Autowired
    public LicenceServiceImpl(LicenceRepository licenceRepository, InspectionRepository inspectionRepository) {
        this.licenceRepository = licenceRepository;
        this.inspectionRepository = inspectionRepository;
    }

    @Override
    public LicenceDAO save(LicenceDAO licenceDAO) {
        Inspection inspection = inspectionRepository.findById(licenceDAO.getPartenaire().getId()).orElse(null);
        if (inspection != null) {
            boolean isNew = (licenceDAO.getId() == null); // ⚡ détection création
            if (isNew) {
                Licence lastLicence = licenceRepository.findFirstByPaysOrderByIdDesc(licenceDAO.getPays());
                long next = 1;
                if (lastLicence != null && lastLicence.getNumerolicence() != null) {
                    String[] parts = lastLicence.getNumerolicence().split("-");
                    if (parts.length >= 4) {
                        try {
                            next = Long.parseLong(parts[3]) + 1;
                        } catch (NumberFormatException e) {
                            next = 1;
                        }
                    }
                }
                String numero = String.format("CERTI-%s-%d-%03d",
                        licenceDAO.getPays().toUpperCase(),
                        LocalDate.now().getYear(),
                        next);

                // ajoute suffixe si besoin
                if (licenceDAO.getIsPro()) {
                    numero += "-PRO";
                } else if (licenceDAO.getIsFree()) {
                    numero += "-FREE";
                }
                licenceDAO.setNumerolicence(numero);
            }
            if (licenceDAO.getIsPro()) {
                licenceDAO.setNumerolicence(licenceDAO.getNumerolicence()+"-PRO");
            } else if (licenceDAO.getIsFree()) {
                licenceDAO.setNumerolicence(licenceDAO.getNumerolicence()+"-FREE");
            }
            licenceDAO.setPartenaire(InspectionDAO.fromEntity(inspection));
            return LicenceDAO.fromEntity(licenceRepository.save(LicenceDAO.toEntity(licenceDAO)));
        }
        return null;
    }

    @Override
    public Page<LicenceDAO> getLicences(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").descending());
        Page<Licence>  licenceS = licenceRepository.findAll(pageable);
        return licenceS.map(LicenceDAO::fromEntity);
    }
}
