package oprag.project.gestionControleDAcces.auth;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(APP_ROOT+"/auth")
@Tag(name = "Authentication", description = "API d'authentification")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Operation(summary = "Authentifier un utilisateur", description = "Cette méthode permet d'authentifier un utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé et authentifié"),
            @ApiResponse(responseCode = "403", description = "Utilisateur non trouvé ou non autorisé")
    })
    @PostMapping(value="/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping(value="/register")
    @Operation(summary = "Creer un utilisateur par", description = "Cette méthode permet de creer un utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur Créer"),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la creation")
    })
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value="/update")
    public ResponseEntity<UtilisateurDAO> update(@RequestBody UtilisateurDAO utilisateurDAO){
        return ResponseEntity.ok(authenticationService.update(utilisateurDAO));
    }


}