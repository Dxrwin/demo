package com.Aerotech.demo.services

import VueloResponse
import com.Aerotech.demo.dto.*
import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.*
import com.Aerotech.demo.entities.*
import com.Aerotech.demo.repository.*
import com.Aerotech.demo.Exception.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class ServicioReporte(
    private val repositorioVuelo: RepositorioVuelo,
    private val repositorioReserva: RepositorioReserva
) {

    fun obtenerVuelosMasReservados(): List<ReporteVueloResponse> {
        val resultados = repositorioVuelo.encontrarVuelosMasReservados()

        return resultados.map { resultado ->
            val vuelo = resultado[0] as Vuelo
            val cantidadReservas = resultado[1] as Long

            // Calcular ingresos del vuelo
            val ingresos = repositorioReserva.encontrarReservasConfirmadasPorVueloId(vuelo.id!!)
                .sumOf { it.montoTotal }

            ReporteVueloResponse(
                vuelo = VueloResponse(vuelo),
                totalReservas = cantidadReservas,
                ingresoTotal = ingresos
            )
        }
    }

    fun obtenerReporteIngresos(request: ReporteIngresosRequest): ReporteIngresosResponse {
        val ingresoTotal = repositorioReserva.obtenerIngresosTotalesPorPeriodo(
            request.fechaInicio,
            request.fechaFin
        ) ?: 0.0

        val reservas = repositorioReserva.findAll()
            .filter { it.fechaReserva >= request.fechaInicio && it.fechaReserva <= request.fechaFin }
            .filter { it.estado == EstadoReserva.CONFIRMADA }

        return ReporteIngresosResponse(
            fechaInicio = request.fechaInicio,
            fechaFin = request.fechaFin,
            ingresoTotal = BigDecimal.valueOf(ingresoTotal),
            totalReservas = reservas.size.toLong()
        )
    }
}