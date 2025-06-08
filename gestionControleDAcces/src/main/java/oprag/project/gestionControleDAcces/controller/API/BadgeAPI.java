package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Badges", description = "API de gestion des Badges")
public interface BadgeAPI {
    @Operation(summary = "Creer un Badge", description = "Cette méthode permet de creer un Badge.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Badge créé"),
            @ApiResponse(responseCode = "404", description = "Badge non créé")
    })
    @PostMapping(value = APP_ROOT+"/Badges/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    BadgeDAO save(@RequestBody BadgeDAO badgeDAO);
    @Operation(summary = "Rechercher un Badge par numero", description = "Cette méthode permet de rechercher un Badge par son numero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Badge trouvé"),
            @ApiResponse(responseCode = "404", description = "Badge non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Badges/findById/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    BadgeDAO findByNumero(@PathVariable("numero") String numero);

    @GetMapping(value = APP_ROOT + "/Badges/getQrCode/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> getQrCode(@PathVariable("numero") String numero);


    @GetMapping(value = APP_ROOT + "/Badges/countAllPerDay", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Object> countAllPerDay();

    @GetMapping(value = APP_ROOT + "/Badges/countAllPerIntervalDays/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Object> countAllPerIntervalDays(@PathVariable("startDate") LocalDate startDate,@PathVariable("endDate") LocalDate endDate);

    @GetMapping(value = APP_ROOT + "/Badges/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BadgeDAO> findAll();

    @Operation(summary = "Supprimer un Badge", description = "Cette méthode permet de supprimer un Badge par ID.")
    @DeleteMapping(value = APP_ROOT + "/Badges/delete/{id}")

    void delete(@PathVariable("id") Integer id);
}
