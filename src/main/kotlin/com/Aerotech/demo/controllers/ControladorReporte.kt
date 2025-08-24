package com.Aerotech.demo.controllers

import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.*
import com.Aerotech.demo.services.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "Generación de reportes - Solo administradores")
class ControladorReporte(
    private val servicioReporte: ServicioReporte
) {

    @GetMapping("/vuelos-mas-reservados")
    @Operation(summary = "Obtener vuelos más reservados")
    fun obtenerVuelosMasReservados(): ResponseEntity<RespuestaApi<List<ReporteVueloResponse>>> {
        val reporte = servicioReporte.obtenerVuelosMasReservados()
        return ResponseEntity.ok(RespuestaApi.exitoso(reporte, "Reporte generado exitosamente"))
    }

    @PostMapping("/ingresos")
    @Operation(summary = "Obtener reporte de ingresos por período")
    fun obtenerReporteIngresos(@Valid @RequestBody request: ReporteIngresosRequest): ResponseEntity<RespuestaApi<ReporteIngresosResponse>> {
        val reporte = servicioReporte.obtenerReporteIngresos(request)
        return ResponseEntity.ok(RespuestaApi.exitoso(reporte, "Reporte de ingresos generado"))
    }

    @GetMapping("/ingresos/periodo")
    @Operation(summary = "Obtener reporte de ingresos por período usando parámetros")
    fun obtenerReporteIngresosPorPeriodo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) fechaInicio: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) fechaFin: LocalDateTime
    ): ResponseEntity<RespuestaApi<ReporteIngresosResponse>> {
        val request = ReporteIngresosRequest(fechaInicio, fechaFin)
        val reporte = servicioReporte.obtenerReporteIngresos(request)
        return ResponseEntity.ok(RespuestaApi.exitoso(reporte, "Reporte de ingresos generado"))
    }
}