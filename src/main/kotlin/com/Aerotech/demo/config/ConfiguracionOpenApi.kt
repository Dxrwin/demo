package com.Aerotech.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License

@Configuration
class ConfiguracionOpenApi {

    @Bean
    fun configuracionPersonalizadaOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("AeroTech - Sistema de Reservas de Vuelos")
                    .description("API REST para el sistema de gestión de reservas de vuelos de AeroTech")
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .name("Equipo AeroTech")
                            .email("soporte@aerotech.com")
                            .url("https://aerotech.com")
                    )
                    .license(
                        License()
                            .name("Licencia MIT")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
    }
}