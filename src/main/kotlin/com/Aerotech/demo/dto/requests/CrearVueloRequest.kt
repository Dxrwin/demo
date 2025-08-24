package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDateTime

data class CrearVueloRequest(
    @field:NotBlank(message = "El número de vuelo es obligatorio")
    @field:Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Formato de vuelo inválido (ej: AT001)")
    val numeroVuelo: String,

    @field:NotBlank(message = "El origen es obligatorio")
    @field:Size(min = 2, max = 100, message = "El origen debe tener entre 2 y 100 caracteres")
    val origen: String,

    @field:NotBlank(message = "El destino es obligatorio")
    @field:Size(min = 2, max = 100, message = "El destino debe tener entre 2 y 100 caracteres")
    val destino: String,

    @field:Future(message = "La fecha de salida debe ser futura")
    val fechaSalida: LocalDateTime,

    val fechaLlegada: LocalDateTime,

    @field:DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    val precio: BigDecimal,

    val aeronaveId: Long
)