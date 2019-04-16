package com.aew.crud_users;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.ApiInfo;

/**
 * Swagger UI configuration class. By default, access the API documentation on
 * http://localhost:8080/swagger-ui.html
 *
 * @author Adrian
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.aew.crud_users.controller"))
                .paths(PathSelectors.regex("/.*")).build().pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact("Adrian E. Wilgenhoff", "https://www.linkedin.com/in/adrian-wilgenhoff/",
                "adrianwilgenhoff@gmail.com");
        return new ApiInfo("CRUD User", "Simple CRUD de Usuarios", "1.0.0", null, contact, "Apache License",
                "https://www.apache.org/licenses/LICENSE-2.0.txt");
    }

}