package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.repository.BadgeRepository;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.InspectionMontantRepository;
import oprag.project.gestionControleDAcces.services.CertificatControlService;
import oprag.project.gestionControleDAcces.validators.CertificatControlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificatControlServiceImpl implements CertificatControlService {
    private final CertificatControlRepository  certificatControlRepository;
    private final BadgeRepository badgeRepository;
    private final InspectionMontantRepository inspectionMontantRepository;
    @Autowired
    public CertificatControlServiceImpl(CertificatControlRepository certificatControlRepository,BadgeRepository badgeRepository, InspectionMontantRepository inspectionMontantRepository) {
        this.certificatControlRepository = certificatControlRepository;
        this.badgeRepository = badgeRepository;
        this.inspectionMontantRepository = inspectionMontantRepository;
    }

    @Override
    public CertificatControlDAO save(CertificatControlDAO certificatControlDAO) {

        List<String> errors= CertificatControlValidator.validate(certificatControlDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le certificat que vous passez n'est pas valide",ErrorCodes.CERTFICAT_NOT_VALID,errors);
        }


        var inspectionMontant= InspectionMontantDAO.fromEntity(inspectionMontantRepository.findAll().stream().findFirst().get());

        String last = this.certificatControlRepository.findTopByOrderByIdDesc();
        long next = 200;
        if (last != null) {
            String[] parts = last.split("-RP-");
            next = Long.parseLong(parts[1]) + 1;
        }
        certificatControlDAO.setNumeroRapport(certificatControlDAO.getUtilisateur().getInspection().getCode()+"-RP-"+next);
        certificatControlDAO.setMontant(inspectionMontant.getMontant());
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
    public long numberOfCertificatControls() {
        return this.findAll().size();
    }

    @Override
    public Double certificatControlsAmount() {
        return this.certificatControlRepository.getTotalMontant();
    }

    @Override
    public List<Map<String, Object>> getCertificatControlsStatsByInspection() {
        List<Object[]> rows = certificatControlRepository.countCertificatControlByInspectionAndMonth();

        // Map temporaire : { Inspection -> [12 mois] }
        Map<String, List<Long>> inspectionData = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String inspectionName = (String) row[0];
            Integer mois = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();

            inspectionData.putIfAbsent(inspectionName, initEmptyList()); // Liste de 12 zéros
            inspectionData.get(inspectionName).set(mois - 1, total); // Index basé sur mois (1 → 12)
        }

        // Transformer en List<Map<String,Object>>
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<Long>> entry : inspectionData.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", entry.getKey());
            item.put("data", entry.getValue());
            result.add(item);
        }

        return result;
    }

    private List<Long> initEmptyList() {
        return new ArrayList<>(Collections.nCopies(12, 0L));
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
