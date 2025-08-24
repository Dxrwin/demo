package com.Aerotech.demo.controllers

import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.*
import com.Aerotech.demo.services.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pasajeros")
@Tag(name = "Pasajeros", description = "Gestión de pasajeros")
class ControladorPasajero(
    private val servicioPasajero: ServicioPasajero
) {

    @GetMapping("/vuelo/{vueloId}")
    @Operation(summary = "Obtener pasajeros de un vuelo", description = "Para empleados y administradores")
    fun obtenerPasajerosPorVuelo(@PathVariable vueloId: Long): ResponseEntity<RespuestaApi<List<PasajeroResponse>>> {
        val pasajeros = servicioPasajero.obtenerPasajerosPorVuelo(vueloId)
        return ResponseEntity.ok(RespuestaApi.exitoso(pasajeros))
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Obtener pasajeros de una reserva")
    fun obtenerPasajerosPorReserva(@PathVariable reservaId: Long): ResponseEntity<RespuestaApi<List<PasajeroResponse>>> {
        val pasajeros = servicioPasajero.obtenerPasajerosPorReserva(reservaId)
        return ResponseEntity.ok(RespuestaApi.exitoso(pasajeros))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pasajero por ID")
    fun obtenerPasajeroPorId(@PathVariable id: Long): ResponseEntity<RespuestaApi<PasajeroResponse>> {
        val pasajero = servicioPasajero.obtenerPasajeroPorId(id)
        return ResponseEntity.ok(RespuestaApi.exitoso(pasajero))
    }
}