package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.IpApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IpGeoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String IP_API_URL = "http://ip-api.com/json/{ip}?fields=status,country,regionName,city,query";

    public String geolocateIp(String ip) {
        try {
            IpApiResponse resp = restTemplate.getForObject(IP_API_URL, IpApiResponse.class, ip);
            if (resp != null && "success".equalsIgnoreCase(resp.getStatus())) {
                String city = resp.getCity() == null ? "" : resp.getCity();
                String region = resp.getRegionName() == null ? "" : resp.getRegionName();
                String country = resp.getCountry() == null ? "" : resp.getCountry();
                // Concatène proprement
                return Stream.of(city, region, country)
                        .filter(s -> s != null && !s.isBlank())
                        .collect(Collectors.joining(", "));
            }
        } catch (Exception e) {
            // log la cause
        }
        return "Localisation inconnue";
    }
}
