//package oprag.project.gestionControleDAcces.config;
//
//import org.springframework.context.annotation.Bean;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import static oprag.project.gestionControleDAcces.constant.Utils.APP_ROOT;
//
//public class SwaggerConfiguration {
//
//    @Bean
//    public Docket api(){
//        return  new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(
//                        new ApiInfoBuilder()
//                                .description("Solution de control d'accès automatisé pour les zones portuaires gabonnaise")
//                                .title("Gestion de OPRAG")
//                                .build()
//                )
//                .groupName("REST API V1")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("oprag.project.gestionControleDAcces"))
//                .paths(PathSelectors.ant(APP_ROOT+"/**"))
//                .build();
//    }
//}
