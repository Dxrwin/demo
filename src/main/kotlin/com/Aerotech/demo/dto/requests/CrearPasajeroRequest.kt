package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

// DTOs para Pasajero
data class CrearPasajeroRequest(
    @field:NotBlank(message = "El nombre es obligatorio")
    @field:Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    val nombre: String,

    @field:NotBlank(message = "El documento es obligatorio")
    @field:Size(min = 5, max = 20, message = "El documento debe tener entre 5 y 20 caracteres")
    val documento: String,

    @field:Min(value = 0, message = "La edad debe ser positiva")
    @field:Max(value = 120, message = "La edad máxima es 120")
    val edad: Int
)