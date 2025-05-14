package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.InspectionDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Inspections", description = "API de gestion des utilisateurs")
public interface InspectionAPI {
    @Operation(summary = "Creer une Inspection", description = "Cette méthode permet de creer une inspection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inspection créé"),
            @ApiResponse(responseCode = "404", description = "inspection non créé")
    })
    @PostMapping(value = APP_ROOT+"/Inspections/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    InspectionDAO save(@RequestBody InspectionDAO inspectionDAO);
    @Operation(summary = "Lister toutes les inspections", description = "Cette méthode permet de retourner toutes les inspections.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inspections retournées"),
            @ApiResponse(responseCode = "404", description = "inspections non trouvés")
    })
    @GetMapping(value = APP_ROOT + "/Inspections/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<InspectionDAO> findAll();

    @Operation(summary = "Rechercher une inspection par Id", description = "Cette méthode permet de rechercher une inspection par son Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection trouvé"),
            @ApiResponse(responseCode = "404", description = "Inspection non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Inspections/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    InspectionDAO findById(@PathVariable("id") Integer id);

    @Operation(summary = "Rechercher une inspection par nom", description = "Cette méthode permet de rechercher une inspection par son nom.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection trouvé"),
            @ApiResponse(responseCode = "404", description = "Inspection non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Inspections/findByNom/{nom}", produces = MediaType.APPLICATION_JSON_VALUE)
    InspectionDAO findByNom(@PathVariable("nom") String nom);

    @Operation(summary = "Supprimer un Inspection", description = "Cette méthode permet de supprimer une Inspections par ID.")
    @DeleteMapping(value = APP_ROOT + "/Inspections/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
