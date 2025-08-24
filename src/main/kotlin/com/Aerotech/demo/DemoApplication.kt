package com.Aerotech.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


/**
 * Aplicación principal del Sistema AeroTech
 * Sistema de gestión de reservas de vuelos desarrollado en Spring Boot con Kotlin
 *
 * Características principales:
 * - Gestión de usuarios con roles (Cliente, Empleado, Administrador)
 * - CRUD completo de aeronaves y vuelos
 * - Sistema de reservas con validación de asientos disponibles
 * - Búsqueda de vuelos por origen, destino y fecha
 * - Cancelación de reservas con restricción de tiempo
 * - Reportes de vuelos más reservados e ingresos
 * - Documentación automática con OpenAPI/Swagger
 * - Soporte para H2 (desarrollo) y MySQL (producción)
 *
 * @author Equipo AeroTech
 * @version 1.0.0
 */

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)

	println("""
        ==========================================
        🛩️  AEROTECH - Sistema de Reservas de Vuelos  🛩️
        ==========================================
        
        ✈️ Aplicación iniciada exitosamente
        
        📚 Documentación API: http://localhost:8080/api/v1/swagger-ui.html
        🗃️ Consola H2 (dev): http://localhost:8080/api/v1/h2-console
        🌐 API Base URL: http://localhost:8080/api/v1/
        
        👥 Usuarios de prueba:
        - Administrador: admin@aerotech.com / admin123
        - Empleado: empleado@aerotech.com / empleado123
        - Cliente: cliente@gmail.com / cliente123
        
        📋 Endpoints principales:
        - POST /autenticacion/registrar
        - POST /autenticacion/iniciar-sesion
        - GET /vuelos
        - POST /vuelos/buscar
        - POST /reservas
        - GET /reservas/mis-reservas
        - GET /reportes/vuelos-mas-reservados
        
        ==========================================
    """.trimIndent())
}
