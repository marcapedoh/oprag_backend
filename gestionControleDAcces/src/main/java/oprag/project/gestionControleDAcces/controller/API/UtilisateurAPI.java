package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
public interface UtilisateurAPI {

    @Operation(summary = "Rechercher un utilisateur par ID", description = "Cette méthode permet de rechercher un utilisateur par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Utilisateurs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDAO findById(@PathVariable("id") Integer id);

    @Operation(summary = "Rechercher un utilisateur par email", description = "Cette méthode permet de rechercher un utilisateur par son email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Utilisateurs/findByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDAO findByEmail(@PathVariable("email") String email);

    @Operation(summary = "Lister tous les utilisateurs", description = "Cette méthode permet de retourner la liste de tous les utilisateurs.")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    @GetMapping(value = APP_ROOT + "/Utilisateurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDAO> findAll();


    @Operation(summary = "Lister tous les utilisateurs par nom d'inspection", description = "Cette méthode permet de retourner la liste de tous les utilisateurs par nomInspection.")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs par nom Inspection")
    @GetMapping(value = APP_ROOT + "/Utilisateurs/findAllByInspectionNom", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDAO> findAllByInspectionNom();

    @GetMapping(value = APP_ROOT + "/Utilisateurs/lastOperationDate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Instant lastOperationDate(@PathVariable("id") Integer id);

    @Operation(summary = "Supprimer un utilisateur", description = "Cette méthode permet de supprimer un utilisateur par ID.")
    @DeleteMapping(value = APP_ROOT + "/Utilisateurs/delete/{id}")
    void delete(@PathVariable("id") Integer id);

    @Operation(summary = "Desactivé un utilisateur", description = "Cette méthode permet de desactiver un utilisateur par ID.")
    @DeleteMapping(value = APP_ROOT + "/Utilisateurs/changeState/{id}")
    void changeState(@PathVariable("id") Integer id);
}
