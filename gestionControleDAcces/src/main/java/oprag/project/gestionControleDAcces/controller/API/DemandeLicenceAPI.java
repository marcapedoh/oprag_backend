package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.DemandeLicenceDAO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;


@Tag(name = "DemandeLicence", description = "API de gestion des DemandeLicence")
public interface DemandeLicenceAPI {
    @Operation(summary = "Creer une DemandeLicence", description = "Cette méthode permet de creer une DemandeLicence.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DemandeLicence créé"),
            @ApiResponse(responseCode = "404", description = "DemandeLicence non créé")
    })
    @PostMapping(value = APP_ROOT+"/DemandeLicence/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    DemandeLicenceDAO save(@RequestBody DemandeLicenceDAO demandeLicenceDAO);

    @GetMapping(value = APP_ROOT + "/DemandeLicence/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<DemandeLicenceDAO> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Supprimer une DemandeLicence", description = "Cette méthode permet de supprimer une DemandeLicence par ID.")
    @DeleteMapping(value = APP_ROOT + "/DemandeLicence/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
