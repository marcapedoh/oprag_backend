package oprag.project.gestionControleDAcces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionControleDAccesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionControleDAccesApplication.class, args);
	}

}
