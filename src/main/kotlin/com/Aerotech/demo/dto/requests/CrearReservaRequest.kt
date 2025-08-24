package com.Aerotech.demo.dto.requests

import jakarta.validation.constraints.Size

// DTOs para Reserva
data class CrearReservaRequest(
    val vueloId: Long,

    @field:Size(min = 1, message = "Debe haber al menos un pasajero")
    val pasajeros: List<CrearPasajeroRequest>
)