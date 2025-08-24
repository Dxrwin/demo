package com.Aerotech.demo.dto.requests

import java.time.LocalDateTime

data class ReporteIngresosRequest(
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime
)