package oprag.project.gestionControleDAcces.controller.API;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Map;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Statistiques", description = "API des Statistiques")
public interface StatsAPI {
    @Operation(summary = "Recherche des indicateurs analytique", description = "Cette méthode permet de rechercher des indicateurs analytiques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indicateurs trouvé"),
            @ApiResponse(responseCode = "404", description = "Indicateurs non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Statistiques/{dateDebut}/{dateFin}/{inspectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  Object stats(@PathVariable("dateDebut") LocalDate dateDebut,@PathVariable("dateFin") LocalDate dateFin,@PathVariable("inspectionId") Long inspectionId);

}
