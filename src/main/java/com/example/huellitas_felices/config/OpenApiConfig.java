package com.example.huellitas_felices.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI huellitasApi() {
        return new OpenAPI()
                .info(new Info().title("Huellitas Felices API")
                        .description("API para gestión de adopción de mascotas")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del proyecto")
                        .url("http://localhost:8080/swagger-ui.html"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingrese el token JWT")));
    }
}
