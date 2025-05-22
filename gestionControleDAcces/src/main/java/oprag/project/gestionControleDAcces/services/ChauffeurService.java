package oprag.project.gestionControleDAcces.services;


import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;

import java.util.List;

public interface ChauffeurService {
    //ChauffeurDAO save(ChauffeurDAO chauffeurDAO);
    ChauffeurDAO findById(Integer id);
    ChauffeurDAO findByUserName(String userName);
    //AuthenticationResponse findByUserNameAndMotDePasse(String userName, String motDePasse);
    List<ChauffeurDAO> findAll();
    void delete(Integer id);
}
