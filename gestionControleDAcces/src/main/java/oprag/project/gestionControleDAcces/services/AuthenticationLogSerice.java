package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.AuthenticationLogDAO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthenticationLogSerice {
    AuthenticationLogDAO save(AuthenticationLogDAO authenticationLogDAO);
    List<AuthenticationLogDAO> findAllByUtilisateurId(Integer utilisateurId);
    List<AuthenticationLogDAO> findAll();
}
