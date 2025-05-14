package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InspectionValidator {
    public static List<String> validate(InspectionDAO inspectionDAO) {
        List<String> errors= new ArrayList<>();
        if(inspectionDAO==null){
            errors.add("l'inspection n'est pas valide");
        }
        assert inspectionDAO != null;
        if(!StringUtils.hasLength(String.valueOf(inspectionDAO.getNom()))){
            errors.add("une inspection doit impérativement avoir de nom");
        }


        return errors;
    }
}
