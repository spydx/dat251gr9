package no.hvl.dat251.gr9.lopbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

// URL -----> http://localhost:8080/swagger-ui.html

@Configuration
@EnableSwagger2
public class ConfigSwagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("no.hvl.dat251.gr9.lopbackend"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "LOP REST API",
                "LOP API",
                "API Production",
                "Terms of service",
                new Contact("Lop Admin", "www.lop.no", "support@lop.no"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
