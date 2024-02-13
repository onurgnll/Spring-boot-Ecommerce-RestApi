package com.ecommerce.app.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce Restful Api")
                        .description("This project builds the backend of an e-commerce application using the Spring Boot framework. It exposes many functional endpoints. You will see the details below. The project utilizes a relational database. ")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Author")
                                .email("onr.gnll@outlook.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github Project Url")
                        .url("https://github.com/onurgnll/Spring-boot-Ecommerce-RestApi"));
    }

}
