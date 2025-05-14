package oprag.project.gestionControleDAcces.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oprag.project.gestionControleDAcces.config.JwtService;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.InspectionRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.EmailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;
    private final InspectionRepository inspectionRepository;
    private final EmailService emailService;
   private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        int countOfConnectionPerUser=0;
//        try {
//            UserDetails user=utilisateurRepository.findUtilisateurByUserName(request.getUsername()).orElseThrow(()-> new EntityNotFoundException("aucun utilisateur trouvé avec ce nom d'utilisateur"));
//            String jwtToken="";
//            if(passwordEncoder.matches(request.getMotDePasse(),user.getPassword())){
//                jwtToken=jwtService.generateToken(user);
//            }
//            UserDetails user1=utilisateurRepository.findUtilisateurByUserName(request.getUsername()).orElseThrow(()-> new EntityNotFoundException("Aucun utilisateur trouvé dans la bd"));
//            if(!StringUtils.hasLength(user1.getPassword())){
//                log.warn("le username de cet utilisateur est null");
//            }
//            countOfConnectionPerUser+=1;
//            return AuthenticationResponse.builder()
//                    .token(jwtToken)
//                    .build();
//        }catch (EntityNotFoundException e1){
//            try {
//                countOfConnectionPerUser+=1;
//                return authenticatePrestataire(request);
//            }catch (EntityNotFoundException e2){
//                countOfConnectionPerUser+=1;
//                return  authenticateAssure(request);
//            }
//
//        }
        return  null;
    }


    public AuthenticationResponse register(RegisterRequest request){
        var utilisateur= utilisateurRepository.findUtilisateurByUserName(request.getUserName());
        var inspection= inspectionRepository.findById(request.getIdInspection());
        if(!utilisateur.isPresent() && inspection.isPresent()){
            var user= Utilisateur.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .userName(request.getUserName())
                    .premiereConnexion(true)
                    .role(UserRole.valueOf(request.getRole()))
                    .email(request.getEmail())
                    .inspection(inspection.get())
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
        }else{
            if(!inspection.isPresent()){
                throw new EntityNotFoundException("l'inspection que vous cherchez afin de creer l'utilisateur n'est pas trouvé ou n'existe pas encore");
            }
        }
        return AuthenticationResponse.builder()
                .token("erreur survenue lors de la persistance")
                .build();
    }


}