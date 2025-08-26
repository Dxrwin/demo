package com.Aerotech.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    @Override
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Aplica a todas las rutas
            //.allowedOrigins(
            //"http://localhost:5500",
            //"http://127.0.0.1:5500"
            //)
            .allowedOrigins("*") // Permite todas las fuentes (cambia esto en producción)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Incluye OPTIONS para preflight
            .allowedHeaders("*")
            .allowCredentials(false) // Cambia a true si usas cookies
            .maxAge(3600)
    }
}