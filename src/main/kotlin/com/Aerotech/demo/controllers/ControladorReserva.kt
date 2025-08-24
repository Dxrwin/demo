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
import java.time.LocalDateTime

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas de vuelos")
class ControladorReserva(
    private val servicioReserva: ServicioReserva
) {

    @PostMapping
    @Operation(summary = "Crear nueva reserva", description = "Para clientes autenticados")
    fun crearReserva(
        @Valid @RequestBody request: CrearReservaRequest,
        @RequestHeader("X-Usuario-Id") clienteId: Long
    ): ResponseEntity<RespuestaApi<ReservaResponse>> {
        val reserva = servicioReserva.crearReserva(request, clienteId)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(RespuestaApi.exitoso(reserva, "Reserva creada exitosamente"))
    }

    @GetMapping
    @Operation(summary = "Obtener todas las reservas", description = "Para empleados y administradores")
    fun obtenerTodasLasReservas(): ResponseEntity<RespuestaApi<List<ReservaResponse>>> {
        val reservas = servicioReserva.obtenerTodasLasReservas()
        return ResponseEntity.ok(RespuestaApi.exitoso(reservas))
    }

    @GetMapping("/mis-reservas")
    @Operation(summary = "Obtener reservas del cliente autenticado")
    fun obtenerMisReservas(@RequestHeader("X-Usuario-Id") clienteId: Long): ResponseEntity<RespuestaApi<List<ReservaResponse>>> {
        val reservas = servicioReserva.obtenerReservasPorCliente(clienteId)
        return ResponseEntity.ok(RespuestaApi.exitoso(reservas))
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar reserva por código")
    fun obtenerReservaPorCodigo(@PathVariable codigo: String): ResponseEntity<RespuestaApi<ReservaResponse>> {
        val reserva = servicioReserva.obtenerReservaPorCodigo(codigo)
        return ResponseEntity.ok(RespuestaApi.exitoso(reserva))
    }

    @GetMapping("/vuelo/{vueloId}")
    @Operation(summary = "Obtener reservas de un vuelo", description = "Para empleados y administradores")
    fun obtenerReservasPorVuelo(@PathVariable vueloId: Long): ResponseEntity<RespuestaApi<List<ReservaResponse>>> {
        val reservas = servicioReserva.obtenerReservasPorVuelo(vueloId)
        return ResponseEntity.ok(RespuestaApi.exitoso(reservas))
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva")
    fun cancelarReserva(
        @PathVariable id: Long,
        @RequestHeader("X-Usuario-Id") clienteId: Long
    ): ResponseEntity<RespuestaApi<ReservaResponse>> {
        val reserva = servicioReserva.cancelarReserva(id, clienteId)
        return ResponseEntity.ok(RespuestaApi.exitoso(reserva, "Reserva cancelada exitosamente"))
    }
}
