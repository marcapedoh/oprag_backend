package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.ChauffeurRepository;
import oprag.project.gestionControleDAcces.services.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ChauffeurServiceImpl implements ChauffeurService {
    private final ChauffeurRepository chauffeurRepository;
    private final CertificatControlRepository certificatControlRepository;


    @Autowired
    public ChauffeurServiceImpl(CertificatControlRepository certificatControlRepository, ChauffeurRepository chauffeurRepository, PasswordEncoder passwordEncoder) {
        this.certificatControlRepository = certificatControlRepository;
        this.chauffeurRepository = chauffeurRepository;
    }



    @Override
    public ChauffeurDAO findById(Integer id) {
        return this.chauffeurRepository.findById(id)
                .map(ChauffeurDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucun chauffeur trouvé pour cet id"));
    }

    @Override
    public ChauffeurDAO findByUserName(String userName) {
        return this.chauffeurRepository.findChauffeurByUserName(userName)
                .map(ChauffeurDAO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucun chauffeur trouvé pour ce userName"));
    }

    @Override
    public List<ChauffeurDAO> findAll() {
        return this.chauffeurRepository.findAll().stream()
                .map(ChauffeurDAO::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {
        assert id != null;
        if(this.certificatControlRepository.findCertificatControlByChauffeurId(id).isEmpty()){
            var chauffeur=findById(id);
            chauffeur.setActive(false);
        }else{
            throw new InvalidOperationException("vous ne pouvez pas supprimé un chauffeur qui est déjà liés à des opérations dans la base de données",ErrorCodes.CHAUFFEUR_EN_COURS_D_EXECUTION);
        }
    }
}
