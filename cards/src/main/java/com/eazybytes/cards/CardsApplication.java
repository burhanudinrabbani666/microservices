package com.eazybytes.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImpl")
@OpenAPIDefinition(info = @Info( //
                title = "Cards Microservice REST API Documentation", //
                description = "EazyBank Card microservices REST API Documentation", //
                version = "v1", //
                contact = @Contact( //
                                name = "burhanudin rabbani", //
                                email = "burhanudinrabbani666@gmail.com", //
                                url = "www.burhanudin.com"), //
                license = @License( //
                                name = "Apache 2.0", //
                                url = "www.burhanudin.com")), //
                externalDocs = @ExternalDocumentation( //
                                description = "EazyBank Card microservices REST API Documentation", //
                                url = "www.burhanudin.com" //
                ) //
)
public class CardsApplication {
        public static void main(String[] args) {
                SpringApplication.run(CardsApplication.class, args);
        }
}
