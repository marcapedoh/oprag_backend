package oprag.project.gestionControleDAcces.validators;

import oprag.project.gestionControleDAcces.dto.VehiculeDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VehiculeValidator {
    public static List<String> validate(VehiculeDAO vehiculeDAO){
        List<String> errors= new ArrayList<>();
        if(vehiculeDAO==null){
            errors.add("le vehicule n'est pas valide");
        }
        assert vehiculeDAO != null;
        if(!StringUtils.hasLength(String.valueOf(vehiculeDAO.getNumeroAssurance()))){
            errors.add("on a besoin de savoir le numero de assurance du véhicule");
        }
        if(!StringUtils.hasLength(String.valueOf(vehiculeDAO.getNumeroCarteGrise()))){
            errors.add("on a besoin de savoir le numero de la carte grise du véhicule");
        }
        if(!StringUtils.hasLength(String.valueOf(vehiculeDAO.getNumeroVisiteTechnique()))){
            errors.add("on a besoin de savoir le numero de la visite technique du véhicule");
        }
        return errors;
    }
}
