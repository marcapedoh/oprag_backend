package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDAO utilisateurDAO) {
        List<String> errors= new ArrayList<>();
        if(utilisateurDAO==null){
            errors.add("l'utilisateur n'est pas valide");
        }
        assert utilisateurDAO != null;
        if(!StringUtils.hasLength(String.valueOf(utilisateurDAO.getNom()))){
            errors.add("un utilisateur on doit pouvoir avoir son nom et de facon unique");
        }
        if(!StringUtils.hasLength(String.valueOf(utilisateurDAO.getEmail()))){
            errors.add("un utilisateur a besoin d'avoir un email");
        }
        if(!StringUtils.hasLength(String.valueOf(utilisateurDAO.getUserName()))){
            errors.add("le username de l'utilisateur ne peut pas etre null");
        }

        if(!StringUtils.hasLength(String.valueOf(utilisateurDAO.getMotDePasse()))){
            errors.add("le mot de passe de l'utilisateur doit pas etre null");
        }


        return errors;
    }
}
