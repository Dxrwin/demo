package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class IniciarSesionRequest(
    @field:Email(message = "El correo debe tener formato válido")
    @field:NotBlank(message = "El correo es obligatorio")
    val correo: String,

    @field:NotBlank(message = "La contraseña es obligatoria")
    val contrasena: String
)