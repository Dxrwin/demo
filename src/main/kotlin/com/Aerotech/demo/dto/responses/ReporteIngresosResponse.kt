package com.Aerotech.demo.dto.responses

import java.math.BigDecimal
import java.time.LocalDateTime

data class ReporteIngresosResponse(
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime,
    val ingresoTotal: BigDecimal,
    val totalReservas: Long
)