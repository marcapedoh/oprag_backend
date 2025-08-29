package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.InspectionMontantDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;


@Tag(name = "MontantInspection", description = "API de gestion du montant du rapport d'inspection")
public interface InspectionMontantAPI {
    @Operation(summary = "Ajouter un montant", description = "Cette méthode permet d'ajouter un montant pour les rapport d'inspection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "montant ajouté"),
            @ApiResponse(responseCode = "404", description = "montant non ajouté cause d'erreur de validation")
    })
    @PostMapping(value = APP_ROOT+"/MontantInspection/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    InspectionMontantDAO save(@RequestBody InspectionMontantDAO inspectionMontantDAO);

    @Operation(summary = "Rechercher un montant d'inspection par Id", description = "Cette méthode permet de rechercher une montant d'inspection par son Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "montant  trouvé"),
            @ApiResponse(responseCode = "404", description = "Inspection non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/MontantInspection/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    InspectionMontantDAO findById(@PathVariable("id") Integer id);


    @Operation(summary = "Rechercher tous les montants d'inspection", description = "Cette méthode permet de rechercher tous les  montants d'inspection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "montants d'inspection  trouvé"),
            @ApiResponse(responseCode = "404", description = "montants d'inspection non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/MontantInspection/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<InspectionMontantDAO> findAll();

    @Operation(summary = "Supprimer montant d'un rapport d'inspection", description = "Cette méthode permet de supprimer un montant d'inspection par ID.")
    @DeleteMapping(value = APP_ROOT + "/MontantInspection/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
