package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Badges", description = "API de gestion des Badges")
@Validated
public interface BadgeAPI {
    @Operation(summary = "Creer un Badge", description = "Cette méthode permet de creer un Badge.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Badge créé"),
            @ApiResponse(responseCode = "404", description = "Badge non créé")
    })
    @PostMapping(value = APP_ROOT+"/Badges/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    BadgeDAO save(@Valid @RequestBody BadgeDAO badgeDAO);
    @Operation(summary = "Rechercher un Badge par numero", description = "Cette méthode permet de rechercher un Badge par son numero.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Badge trouvé"),
            @ApiResponse(responseCode = "404", description = "Badge non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/Badges/findById/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    BadgeDAO findByNumero(@PathVariable("numero") @NotBlank(message = "Le numéro du badge est obligatoire") String numero);

    @GetMapping(value = APP_ROOT + "/Badges/getQrCode/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> getQrCode(@PathVariable("numero") String numero);

    @GetMapping(value = APP_ROOT + "/Badges/numberOfBadges", produces = MediaType.APPLICATION_JSON_VALUE)
    long numberOfBadges();

    @GetMapping(value = APP_ROOT + "/Badges/numberOfBadgePerInspection", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> numberOfBadgePerInspection();


    @GetMapping(value = APP_ROOT + "/Badges/countAllPerDay", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Object> countAllPerDay();

    @GetMapping(value = APP_ROOT + "/Badges/countAllPerIntervalDays/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Object> countAllPerIntervalDays(@PathVariable("startDate") LocalDate startDate,@PathVariable("endDate") LocalDate endDate);

    @GetMapping(value = APP_ROOT + "/Badges/all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<BadgeDAO> findAll(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size);

    @GetMapping(value = APP_ROOT + "/Badges/findAllPerInspecteurId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BadgeDAO> findAllPerInspecteurId(@PathVariable("id") @Positive(message = "L'ID de l'inspecteur doit être positif") Integer id);

    @Operation(summary = "Supprimer un Badge", description = "Cette méthode permet de supprimer un Badge par ID.")
    @DeleteMapping(value = APP_ROOT + "/Badges/delete/{id}")
    void delete(@PathVariable("id") @Positive(message = "L'ID du badge doit être positif") Integer id);
}
