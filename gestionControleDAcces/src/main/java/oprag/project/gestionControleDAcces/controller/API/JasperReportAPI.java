package oprag.project.gestionControleDAcces.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import oprag.project.gestionControleDAcces.dto.InspectionCardData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@Tag(name = "Reports", description = "API de gestion des Reports")
public interface JasperReportAPI {
    @Operation(summary = "Creer une Report", description = "Cette méthode permet de creer un Report.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report créé"),
            @ApiResponse(responseCode = "404", description = "Report non créé")
    })
    @PostMapping(value = APP_ROOT+"/Reports/exportReport/{reportFormat}/{certificatControlId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> exportReport(@PathVariable("reportFormat") String reportFormat, @PathVariable("certificatControlId")  Integer certificatControlId) throws IOException, JRException;


    @Operation(summary = "Creer premiere face de la carte d'inspection", description = "Cette méthode permet de creer une carte d'inspectin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "carte créé"),
            @ApiResponse(responseCode = "404", description = "carte non créé")
    })
    @PostMapping(value = APP_ROOT+"/Reports/exportCard",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> exportReport(@RequestBody InspectionCardData inspectionCardData) throws IOException, JRException;

    @GetMapping(value = APP_ROOT+"/Reports/exportReportForQrCode/{reportFormat}/{certificatControlId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<byte[]> exportReportForQrCode(@PathVariable("reportFormat") String reportFormat, @PathVariable("certificatControlId")  Integer certificatControlId) throws IOException, JRException;

}
