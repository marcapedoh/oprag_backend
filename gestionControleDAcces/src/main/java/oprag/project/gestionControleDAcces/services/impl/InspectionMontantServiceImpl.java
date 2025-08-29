package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.repository.InspectionMontantRepository;
import oprag.project.gestionControleDAcces.services.InspectionMontantService;
import oprag.project.gestionControleDAcces.validators.CertificatControlValidator;
import oprag.project.gestionControleDAcces.validators.InspectionMontantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class InspectionMontantServiceImpl implements InspectionMontantService {

    private final InspectionMontantRepository inspectionMontantRepository;

    @Autowired
    public InspectionMontantServiceImpl(InspectionMontantRepository inspectionMontantRepository) {
        this.inspectionMontantRepository = inspectionMontantRepository;
    }

    @Override
    public InspectionMontantDAO save(InspectionMontantDAO inspectionMontantDAO) {
        List<String> errors= InspectionMontantValidator.validate(inspectionMontantDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le montant du rapport d'inspection n'est pas valide", ErrorCodes.INSPECTION_MONTANT_NOT_VALID,errors);
        }
        this.inspectionMontantRepository.deleteAll();
        return InspectionMontantDAO.fromEntity(
                inspectionMontantRepository.save(
                        InspectionMontantDAO.toEntity(inspectionMontantDAO)
                )
        );
    }

    @Override
    public InspectionMontantDAO findById(Integer id) {
        return this.inspectionMontantRepository.findById(id)
                .map(InspectionMontantDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucun montant d'inspection trouvé pour cet id"));
    }

    @Override
    public List<InspectionMontantDAO> findAll() {
        return this.inspectionMontantRepository.findAll().stream()
                .map(InspectionMontantDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id!=null;
        inspectionMontantRepository.deleteById(id);
    }
}
