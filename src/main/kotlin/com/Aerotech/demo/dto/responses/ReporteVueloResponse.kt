package com.Aerotech.demo.dto.responses

import VueloResponse
import java.math.BigDecimal

// DTOs para Reportes
data class ReporteVueloResponse(
    val vuelo: VueloResponse,
    val totalReservas: Long,
    val ingresoTotal: BigDecimal
)
