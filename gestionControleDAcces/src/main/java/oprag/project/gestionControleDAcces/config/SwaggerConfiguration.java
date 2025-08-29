package oprag.project.gestionControleDAcces.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

   @Bean
    public OpenAPI customOpenAPI() {
       return new OpenAPI()
               .servers(List.of(
                       new Server()
                               .url("https://badge.routeafrique.com:1020")
                               .description("Server Production"),
                       new Server()
                               .url("https://dev.routeafrique.com:2020")
                               .description("Server Development"),
                       new Server()
                               .url("https://staging.routeafrique.com")
                               .description("Server Staging"),
                       new Server()
                               .url("http://localhost:8080")
                               .description("Server Production")
               ));

   }
}
