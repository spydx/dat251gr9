package no.hvl.dat251.gr9.lopbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

// URL -----> http://localhost:8080/swagger-ui.html

@Configuration
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
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("LOP REST API")
                .description("LOP API")
                .build();
    }
}