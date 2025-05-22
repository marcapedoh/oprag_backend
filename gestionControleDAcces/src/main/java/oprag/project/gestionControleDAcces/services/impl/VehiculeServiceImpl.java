package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.dto.VehiculeDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.VehiculeRepository;
import oprag.project.gestionControleDAcces.services.VehiculeService;
import oprag.project.gestionControleDAcces.validators.InspectionValidator;
import oprag.project.gestionControleDAcces.validators.VehiculeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeRepository vehiculeRepository;
    private CertificatControlRepository certificatControlRepository;
    @Autowired
    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public VehiculeDAO save(VehiculeDAO vehiculeDAO) {
        if(vehiculeRepository.findVehiculeByNumeroCarteGrise(vehiculeDAO.getNumeroCarteGrise()).isPresent()){
            throw new InvalidOperationException("ce vehicule existe déjà dans la base de donnée", ErrorCodes.VEHICULE_ALREADY_EXIST);
        }
        List<String> errors= VehiculeValidator.validate(vehiculeDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le vehicule que vous passez n'est pas valide",ErrorCodes.VEHICULE_NOT_VALID,errors);
        }
        vehiculeDAO.setActive(true);
        return VehiculeDAO.fromEntity(
                vehiculeRepository.save(
                        VehiculeDAO.toEntity(vehiculeDAO)
                )
        );
    }

    @Override
    public VehiculeDAO findById(Integer id) {
        return this.vehiculeRepository.findById(id)
                .map(VehiculeDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Vehicule que vous chercher avec cet id n'est pas trouvé",ErrorCodes.VEHICULE_NOT_FOUND));
    }

    @Override
    public VehiculeDAO findByNumeroCarteGrise(String numeroCarteGrise) {
        return this.vehiculeRepository.findVehiculeByNumeroCarteGrise(numeroCarteGrise)
                .map(VehiculeDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Véhicule non trouvé pour ce numéro de carte grise",ErrorCodes.VEHICULE_NOT_FOUND));
    }

    @Override
    public List<VehiculeDAO> findAll() {
        return this.vehiculeRepository.findAll().stream()
                .map(VehiculeDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id != null;
        if(this.certificatControlRepository.findCertificatControlByVehiculeId(id).isEmpty()){
            var vehicule=findById(id);
            vehicule.setActive(false);
            VehiculeDAO.fromEntity(this.vehiculeRepository.save(VehiculeDAO.toEntity(vehicule)));
        }else{
            throw new InvalidOperationException("vous ne pouvez pas supprimé un chauffeur qui est déjà liés à des opérations dans la base de données",ErrorCodes.CHAUFFEUR_EN_COURS_D_EXECUTION);
        }
    }
}
