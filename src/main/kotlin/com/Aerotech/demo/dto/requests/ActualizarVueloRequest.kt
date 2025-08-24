package com.Aerotech.demo.dto.requests

import java.math.BigDecimal
import java.time.LocalDateTime

data class ActualizarVueloRequest(
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val fechaLlegada: LocalDateTime,
    val precio: BigDecimal
)
