package com.Aerotech.demo.dto.requests

import com.Aerotech.demo.entities.*
import jakarta.validation.constraints.*

// DTOs para Usuario
data class CrearUsuarioRequest(
    @field:Email(message = "Email debe tener formato válido")
    @field:NotBlank(message = "Email es obligatorio")
    val email: String,

    @field:NotBlank(message = "Nombre es obligatorio")
    @field:Size(min = 2, max = 100, message = "Nombre debe tener entre 2 y 100 caracteres")
    val name: String,

    @field:NotBlank(message = "Password es obligatorio")
    @field:Size(min = 6, message = "Password debe tener al menos 6 caracteres")
    val password: String,

    val role: RolUsuario = RolUsuario.CLIENTE
)