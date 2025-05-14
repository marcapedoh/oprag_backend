package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CertificatControlValidator {
    public static List<String> validate(CertificatControlDAO certificatControlDAO) {
        List<String> errors= new ArrayList<>();
        if(certificatControlDAO==null){
            errors.add("le certificat de control n'est pas valide");
        }
        assert certificatControlDAO != null;
        if(!StringUtils.hasLength(String.valueOf(certificatControlDAO.getNumeroRapport()))){
            errors.add("le numero de rapport ne doit pas être null");
        }
        if(!StringUtils.hasLength(String.valueOf(certificatControlDAO.getSociete()))){
            errors.add("la societe delivrante de certificate ne peut pas etre null");
        }

        return errors;
    }
}
