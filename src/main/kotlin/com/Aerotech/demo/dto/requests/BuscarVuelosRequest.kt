package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class BuscarVuelosRequest(
    @field:NotBlank(message = "El origen es obligatorio")
    val origen: String,

    @field:NotBlank(message = "El destino es obligatorio")
    val destino: String,

    val fechaSalida: LocalDateTime
)