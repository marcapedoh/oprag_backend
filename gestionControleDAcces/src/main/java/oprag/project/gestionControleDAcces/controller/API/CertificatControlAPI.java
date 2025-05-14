package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "CertificatControls", description = "API de gestion des CertificatControls")
public interface CertificatControlAPI {
    @Operation(summary = "Creer une CertificatControl", description = "Cette méthode permet de creer un CertificatControl.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CertificatControl créé"),
            @ApiResponse(responseCode = "404", description = "CertificatControl non créé")
    })
    @PostMapping(value = APP_ROOT+"/CertificatControls/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)

    CertificatControlDAO save(@RequestBody CertificatControlDAO certificatControlDAO);

    @Operation(summary = "Rechercher un CertificatControl par Id", description = "Cette méthode permet de rechercher un CertificatControl par son Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CertificatControl trouvé"),
            @ApiResponse(responseCode = "404", description = "CertificatControl non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/CertificatControls/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)

    CertificatControlDAO findById(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/CertificatControls/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CertificatControlDAO> findAll();

    @Operation(summary = "Supprimer un CertificatControl", description = "Cette méthode permet de supprimer un CertificatControl par ID.")
    @DeleteMapping(value = APP_ROOT + "/CertificatControls/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
