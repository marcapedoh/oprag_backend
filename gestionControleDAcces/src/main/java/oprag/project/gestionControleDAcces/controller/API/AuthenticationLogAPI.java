package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.AuthenticationLogDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;


@Tag(name = "AuthenticationLog", description = "API de gestion des AuthenticationLog")
public interface AuthenticationLogAPI {
    @Operation(summary = "Creer un AuthenticationLog", description = "Cette méthode permet de creer un AuthenticationLog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AuthenticationLog créé"),
            @ApiResponse(responseCode = "404", description = "AuthenticationLog non créé")
    })
    @PostMapping(value = APP_ROOT+"/AuthenticationLog/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationLogDAO save(@RequestBody AuthenticationLogDAO authenticationLogDAO);

    @Operation(summary = "Rechercher un AuthenticationLog par utilisateur", description = "Cette méthode permet de rechercher un Badge par id utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AuthenticationLog trouvé"),
            @ApiResponse(responseCode = "404", description = "AuthenticationLog non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/AuthenticationLog/findAllByUtilisateurId/{utilisateurId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AuthenticationLogDAO> findAllByUtilisateurId(@PathVariable("utilisateurId") Integer utilisateurId);

    @GetMapping(value = APP_ROOT + "/AuthenticationLog/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AuthenticationLogDAO> findAll();
}
