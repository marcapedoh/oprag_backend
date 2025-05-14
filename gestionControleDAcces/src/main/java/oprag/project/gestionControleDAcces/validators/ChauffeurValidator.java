package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ChauffeurValidator {

    public static List<String> validate(ChauffeurDAO chauffeurDAO) {
        List<String> errors= new ArrayList<>();
        if(chauffeurDAO==null){
            errors.add("le chauffeur n'est pas valide");
        }
        assert chauffeurDAO != null;
        if(!StringUtils.hasLength(String.valueOf(chauffeurDAO.getNumeroPermis()))){
            errors.add("le numero de permis est un champs très important");
        }
        if(!StringUtils.hasLength(String.valueOf(chauffeurDAO.getNumeroCertificatMedicalValide()))){
            errors.add("le numero de certificat valide est important egalement conformement à la restriction lié au chauffeur");
        }

        return errors;
    }
}
