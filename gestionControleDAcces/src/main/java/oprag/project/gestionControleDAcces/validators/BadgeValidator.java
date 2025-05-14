package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.dto.VehiculeDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BadgeValidator {
    public static List<String> validate(BadgeDAO badgeDAO) {
        List<String> errors= new ArrayList<>();
        if(badgeDAO==null){
            errors.add("le badge n'est pas valide");
        }
        assert badgeDAO != null;
        if(!StringUtils.hasLength(String.valueOf(badgeDAO.getNumero()))){
            errors.add("on a besoin de savoir le numero du badge");
        }
        if(!StringUtils.hasLength(String.valueOf(badgeDAO.getNumeroParc()))){
            errors.add("le numero du parc ne peut pas etre null");
        }

        return errors;
    }
}
