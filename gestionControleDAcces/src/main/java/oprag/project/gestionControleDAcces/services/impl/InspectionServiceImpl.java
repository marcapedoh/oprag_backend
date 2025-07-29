package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.repository.InspectionRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.InspectionService;
import oprag.project.gestionControleDAcces.validators.InspectionValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class InspectionServiceImpl implements InspectionService {

    private final InspectionRepository inspectionRepository;
    private final UtilisateurRepository utilisateurRepository;

    public InspectionServiceImpl(InspectionRepository inspectionRepository, UtilisateurRepository utilisateurRepository) {
        this.inspectionRepository = inspectionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public InspectionDAO save(InspectionDAO inspectionDAO) {
        if(!inspectionRepository.findInspectionByNom(inspectionDAO.getNom()).isEmpty()){
            throw new InvalidOperationException("cette inspection existe déjà dans la base de donnée", ErrorCodes.INSPECTION_EXIST);
        }
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateCondensee = LocalDate.now().format(formatter);
        inspectionDAO.setStatus(true);
        inspectionDAO.setCodeInspection(inspectionDAO.getCode().substring(0,3).toUpperCase()+"-"+inspectionDAO.getType().toString().substring(0,3)+"-"+ dateCondensee+"-"+random.nextInt(99));
        List<String> errors= InspectionValidator.validate(inspectionDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("l'inspection que vous passez n'est pas valide",ErrorCodes.INSPECTION_NOT_VALID,errors);
        }
        return InspectionDAO.fromEntity(
                inspectionRepository.save(
                        InspectionDAO.toEntity(inspectionDAO)
                )
        );
    }

    @Override
    public List<InspectionDAO> findAll() {
        return this.inspectionRepository.findAll().stream()
                .map(InspectionDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public InspectionDAO findById(Integer id) {
        return this.inspectionRepository.findById(id)
                .map(InspectionDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucune inspection trouvé pour cet id",ErrorCodes.INSPECTION_NOT_FOUND));
    }

    @Override
    public List<Long> pieChartStatsData() {
        return Arrays.asList(this.inspectionRepository.countByStatusTrue(),this.inspectionRepository.countByStatusFalse());
    }

    @Override
    public InspectionDAO findByNom(String nom) {
        return this.inspectionRepository.findInspectionByNom(nom)
                .map(InspectionDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucune inspection trouvé pour cet nom",ErrorCodes.INSPECTION_NOT_FOUND));

    }

    @Override
    public void delete(Integer id) {
        if(this.utilisateurRepository.findUtilisateurByInspectionId(id).isEmpty()){
            this.inspectionRepository.deleteById(id);
        }else{
            throw new InvalidOperationException("vous ne pouvez pas supprimé une inspection qui est déjà liés à des opérations dans la base de données",ErrorCodes.INSPECTION_EN_COURS_D_EXECUTION);

        }
    }
}
