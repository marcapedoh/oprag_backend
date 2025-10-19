package oprag.project.gestionControleDAcces.services.impl;


import jakarta.servlet.http.HttpServletRequest;
import oprag.project.gestionControleDAcces.dto.AuthenticationLogDAO;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.models.Utilisateur;
import oprag.project.gestionControleDAcces.repository.AuthenticationLogRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.AuthenticationLogSerice;
import oprag.project.gestionControleDAcces.services.IpGeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthentificationLogServiceImpl implements AuthenticationLogSerice {

    private AuthenticationLogRepository  authenticationLogRepository;
    private UtilisateurRepository utilisateurRepository;

    private  IpGeoService ipGeoService;
    private  HttpServletRequest request;

    @Autowired
    public AuthentificationLogServiceImpl(AuthenticationLogRepository authenticationLogRepository, UtilisateurRepository utilisateurRepository, IpGeoService ipGeoService,HttpServletRequest request) {
        this.authenticationLogRepository = authenticationLogRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.ipGeoService=ipGeoService;
        this.request=request;
    }

    @Override
    public AuthenticationLogDAO save(AuthenticationLogDAO authenticationLogDAO) {
        Utilisateur utilisateur=this.utilisateurRepository.findById(authenticationLogDAO.getUtilisateur().getId()).orElse(null);
        if(utilisateur!=null){
            authenticationLogDAO.setDate(LocalDateTime.now());
            String ip = getClientIp(request);
            authenticationLogDAO.setDate(LocalDateTime.now());
            String locationName = ipGeoService.geolocateIp(ip);
            authenticationLogDAO.setLocalisation(locationName);
            authenticationLogDAO.setUtilisateur(UtilisateurDAO.fromEntity(utilisateur));
            return AuthenticationLogDAO.fromEntity(authenticationLogRepository.save(AuthenticationLogDAO.toEntity(authenticationLogDAO)));
        }
        return null;

    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) return realIp.trim();
        return request.getRemoteAddr();
    }

    @Override
    public List<AuthenticationLogDAO> findAllByUtilisateurId(Integer utilisateurId) {
        return this.authenticationLogRepository.findAllByUtilisateurId(utilisateurId).stream()
                .map(AuthenticationLogDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthenticationLogDAO> findAll() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return this.authenticationLogRepository.findByDateBetween(start,end).stream()
                .map(AuthenticationLogDAO::fromEntity)
                .collect(Collectors.toList());
    }
}
