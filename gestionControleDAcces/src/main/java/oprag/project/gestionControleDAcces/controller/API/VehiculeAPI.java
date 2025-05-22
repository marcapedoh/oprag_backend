package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.VehiculeDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Vehicules", description = "API de gestion des Chauffeurs")
public interface VehiculeAPI {
    @Operation(summary = "Creer un Vehicule", description = "Cette méthode permet de creer un Véhicule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicule créé"),
            @ApiResponse(responseCode = "404", description = "Vehicule non créé")
    })
    @PostMapping(value = APP_ROOT+"/Vehicules/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDAO save(@RequestBody VehiculeDAO vehiculeDAO);

    @Operation(summary = "Rechercher un véhicule par Id", description = "Cette méthode permet de rechercher un véhicule par son Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Vehicules/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDAO findById(@PathVariable("id") Integer id);

    @Operation(summary = "Rechercher un Véhicule par son numeroCarteGrise", description = "Cette méthode permet de rechercher un vehicule par sa carte grise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chauffeur trouvé"),
            @ApiResponse(responseCode = "404", description = "Chauffeur non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Vehicules/findByNumeroCarteGrise/{numeroCarteGrise}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDAO findByNumeroCarteGrise(@PathVariable("numeroCarteGrise") String numeroCarteGrise);

    @Operation(summary = "Lister tous les Véhicules", description = "Cette méthode permet de retourner tous les véhicules.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule retournés"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvés")
    })
    @GetMapping(value = APP_ROOT + "/Vehicules/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VehiculeDAO> findAll();

    @Operation(summary = "Supprimer un Véhicule", description = "Cette méthode permet de supprimer un véhicule par ID.")
    @DeleteMapping(value = APP_ROOT + "/Vehicules/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
