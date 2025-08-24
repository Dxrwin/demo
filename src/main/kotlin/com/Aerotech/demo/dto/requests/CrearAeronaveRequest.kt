package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDateTime

// DTOs para Vuelo
data class CrearAeronaveRequest(
    @field:NotBlank(message = "El modelo es obligatorio")
    @field:Size(min = 2, max = 50, message = "El modelo debe tener entre 2 y 50 caracteres")
    val modelo: String,

    @field:Positive(message = "La capacidad debe ser positiva")
    @field:Min(value = 1, message = "La capacidad mínima es 1")
    @field:Max(value = 1000, message = "La capacidad máxima es 1000")
    val capacidad: Int
)
