package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.auth.AuthenticationResponse;
import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Chauffeurs", description = "API de gestion des Chauffeurs")
public interface ChauffeurAPI {
    @Operation(summary = "Creer un Chauffeur", description = "Cette méthode permet de creer un chauffeur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "chauffeur créé"),
            @ApiResponse(responseCode = "404", description = "chauffeur non créé")
    })
    @PostMapping(value = APP_ROOT+"/Chauffeurs/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDAO register(@RequestBody ChauffeurDAO chauffeurDAO);

    @Operation(summary = "Rechercher un chauffeur par Id", description = "Cette méthode permet de rechercher un chauffeur par son Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chauffeur trouvé"),
            @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Chauffeurs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDAO findById(@PathVariable("id") Integer id);

    @Operation(summary = "Rechercher un chauffeur par son username", description = "Cette méthode permet de rechercher un chauffeur par son username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chauffeur trouvé"),
            @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Chauffeurs/findByUserName/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDAO findByUserName(@PathVariable("userName") String userName);

    @Operation(summary = "Lister tous les chauffeurs", description = "Cette méthode permet de retourner tous les chauffeurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chauffeurs retournées"),
            @ApiResponse(responseCode = "404", description = "Chauffeurs non trouvés")
    })
    @GetMapping(value = APP_ROOT + "/Chauffeurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ChauffeurDAO> findAll();
    @Operation(summary = "Rechercher un chauffeur par son username et son mot de passe", description = "Cette méthode permet de rechercher un chauffeur par son username et son mot de passe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chauffeur trouvé"),
            @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Chauffeurs/authenticate/{userName}/{motDePasse}", produces = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponse authenticate(@PathVariable("userName") String userName,@PathVariable("motDePasse") String motDePasse);

    @Operation(summary = "Supprimer un Chauffeur", description = "Cette méthode permet de supprimer un chauffeur par ID.")
    @DeleteMapping(value = APP_ROOT + "/Chauffeurs/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
