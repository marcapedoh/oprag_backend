package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.LicenceDAO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;


@Tag(name = "Licence", description = "API de gestion des Licences")
public interface LicenceAPI {
    @Operation(summary = "Creer une Licence", description = "Cette méthode permet de creer une Licence.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Licence créé"),
            @ApiResponse(responseCode = "404", description = "Licence non créé")
    })
    @PostMapping(value = APP_ROOT+"/Licence/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    LicenceDAO save (@RequestBody LicenceDAO licenceDAO);

    @GetMapping(value = APP_ROOT + "/Licence/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<LicenceDAO> getLicences(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size);
}
