package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InspectionMontantValidator {
    public static List<String> validate(InspectionMontantDAO inspectionMontantDAO) {
        List<String> errors= new ArrayList<>();
        if(inspectionMontantDAO==null){
            errors.add("le montant du rapport d'inspection n'est pas valide");
        }
        assert inspectionMontantDAO != null;
        if(inspectionMontantDAO.getMontant()==null){
            errors.add("le montant du rapport d'inspection doit impérativement etre fourni");
        }


        return errors;
    }
}
