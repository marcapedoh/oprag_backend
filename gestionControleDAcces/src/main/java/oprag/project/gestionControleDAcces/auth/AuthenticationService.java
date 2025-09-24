package oprag.project.gestionControleDAcces.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oprag.project.gestionControleDAcces.config.JwtService;
import oprag.project.gestionControleDAcces.dto.ChauffeurDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.exception.InvalidEntityException;
import oprag.project.gestionControleDAcces.exception.InvalidOperationException;
import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.ChauffeurRepository;
import oprag.project.gestionControleDAcces.repository.InspectionRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.EmailService;
import oprag.project.gestionControleDAcces.validators.ChauffeurValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final InspectionRepository inspectionRepository;
    private final EmailService emailService;
   private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        String jwtToken;
        var user=this.utilisateurRepository.findUtilisateurByUserName(request.getUsername()).orElseThrow(()-> new EntityNotFoundException("Aucun Utilisateur trouvé pour cet userName"));
        UserDetails utilisateur= this.utilisateurRepository.findUtilisateurByUserName(request.getUsername()).orElseThrow(()-> new EntityNotFoundException("Aucun Utilisateur trouvé pour cet userName"));
        if(passwordEncoder.matches(request.getMotDePasse(),utilisateur.getPassword())){
            jwtToken=jwtService.generateToken(utilisateur);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .userId(user.getId())
                    .active(user.isActive())
                    .nom(user.getNom())
                    .prenom(user.getPrenom())
                    .role(user.getRole())
                    .signaturePresence(user.getSignature() != null)
                    .inspectionId(user.getInspection()!=null?user.getInspection().getId():null)
                    .otpNumber(user.getOtpNumber())
                    .inspectionCode(user.getInspection()!=null?user.getInspection().getCode():null)
                    .inspectionName(user.getInspection()!=null?user.getInspection().getNom():null)
                    .build();
        }
        return null;
    }

    public ChauffeurDAO registerChauffeur(ChauffeurDAO chauffeurDAO){
        if(chauffeurRepository.findByNom(chauffeurDAO.getNom()).isPresent()){
            throw new InvalidOperationException("ce chauffeur existe déjà dans la base de donnée", ErrorCodes.CHAUFFEUR_ALREADY_EXIST);
        }
        List<String> errors= ChauffeurValidator.validate(chauffeurDAO);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("le chauffeur que vous passez n'est pas valide",ErrorCodes.CHAUFFEUR_NOT_VALID,errors);
        }
        chauffeurDAO.setActive(true);

        chauffeurDAO.setMotDePasse(passwordEncoder.encode(chauffeurDAO.getNom()+chauffeurDAO.getPrenom()));
        return ChauffeurDAO.fromEntity(
                chauffeurRepository.save(
                        ChauffeurDAO.toEntity(chauffeurDAO)
                )
        );
    }
    public AuthenticationResponse authenticateChauffeur(String userName, String motDePasse) {
        String jwtToken;
        UserDetails chauffeur= this.chauffeurRepository.findChauffeurByUserName(userName).orElseThrow(()-> new EntityNotFoundException("Aucun chauffeur trouvé pour cet userName"));
        if(passwordEncoder.matches(motDePasse,chauffeur.getPassword())){
            jwtToken=jwtService.generateToken(chauffeur);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return null;

    }
    public AuthenticationResponse register(RegisterRequest request){
        var utilisateur= utilisateurRepository.findUtilisateurByUserName(request.getUserName());
        var inspection= inspectionRepository.findByCode((request.getIdInspection())).orElse(null);

        if(utilisateur.isEmpty() ){
            var user= Utilisateur.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .userName(request.getUserName())
                    .active(true)
                    .premiereConnexion(true)
                    .role(UserRole.valueOf(request.getRole()))
                    .email(request.getEmail())
                    .inspection(inspection)
                    .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                    .build();
            if(!String.valueOf(user.getRole()).equals("INSPECTEUR")){
                user.setSignature(null);
            }
            Random random = new Random();
            int otpNumber=random.nextInt(9999);
            this.emailService.sendEmail(request.getEmail(),"PIN d'accès","votre PIN d'accès pour la plateforme est: "+otpNumber);
            user.setOtpNumber(otpNumber);
            utilisateurRepository.save(user);
            var jwtToken=jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return AuthenticationResponse.builder()
                .token("erreur survenue lors de la persistance")
                .build();
    }

    public UtilisateurDAO update(UtilisateurDAO utilisateurDAO){
        if(StringUtils.hasLength(utilisateurDAO.getMotDePasse())){
            utilisateurDAO.setMotDePasse(passwordEncoder.encode(utilisateurDAO.getMotDePasse()));
        }
        return UtilisateurDAO.fromEntity(
                this.utilisateurRepository.save(UtilisateurDAO.toEntity(utilisateurDAO))
        );
    }


}