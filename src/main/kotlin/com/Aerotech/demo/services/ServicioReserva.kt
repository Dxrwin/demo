package com.Aerotech.demo.services

import com.Aerotech.demo.dto.*
import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.*
import com.Aerotech.demo.entities.*
import com.Aerotech.demo.repository.*
import com.Aerotech.demo.Exception.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*


@Service
@Transactional
class ServicioReserva(
    private val repositorioReserva: RepositorioReserva,
    private val repositorioVuelo: RepositorioVuelo,
    private val repositorioUsuario: RepositorioUsuario,
    private val repositorioPasajero: RepositorioPasajero
) {

    fun crearReserva(request: CrearReservaRequest, clienteId: Long): ReservaResponse {
        val vuelo = repositorioVuelo.findById(request.vueloId)
            .orElseThrow { RecursoNoEncontradoException("Vuelo no encontrado con ID: ${request.vueloId}") }

        val cliente = repositorioUsuario.findById(clienteId)
            .orElseThrow { RecursoNoEncontradoException("Cliente no encontrado con ID: $clienteId") }

        // Validaciones de reglas de negocio
        if (vuelo.estado != EstadoVuelo.PROGRAMADO) {
            throw ReglaNegocioException("Solo se pueden reservar vuelos con estado PROGRAMADO")
        }

        if (vuelo.obtenerAsientosDisponibles() < request.pasajeros.size) {
            throw ReglaNegocioException("No hay suficientes asientos disponibles. Disponibles: ${vuelo.obtenerAsientosDisponibles()}")
        }

        // Generar código de reserva único
        val codigoReserva = generarCodigoReserva()

        val montoTotal = vuelo.precio.multiply(BigDecimal(request.pasajeros.size))

        val reserva = Reserva(
            codigoReserva = codigoReserva,
            vuelo = vuelo,
            cliente = cliente,
            montoTotal = montoTotal
        )

        val reservaGuardada = repositorioReserva.save(reserva)

        // Crear pasajeros
        val pasajeros = request.pasajeros.map { pasajeroRequest ->
            Pasajero(
                nombre = pasajeroRequest.nombre,
                documento = pasajeroRequest.documento,
                edad = pasajeroRequest.edad,
                reserva = reservaGuardada
            )
        }

        repositorioPasajero.saveAll(pasajeros)

        // Actualizar la reserva con los pasajeros
        val reservaActualizada = reservaGuardada.copy(pasajeros = pasajeros)

        return ReservaResponse(reservaActualizada)
    }

    fun cancelarReserva(reservaId: Long, clienteId: Long): ReservaResponse {
        val fechaActualMas2Horas = LocalDateTime.now().plusHours(2)

        val reserva = repositorioReserva.encontrarReservaCancelable(reservaId, fechaActualMas2Horas)
            .orElseThrow { ReglaNegocioException("No se puede cancelar la reserva. Debe ser al menos 2 horas antes del vuelo") }

        // Verificar que la reserva pertenezca al cliente
        if (reserva.cliente.id != clienteId) {
            throw NoAutorizadoException("No tienes permisos para cancelar esta reserva")
        }

        val reservaCancelada = reserva.copy(estado = EstadoReserva.CANCELADA)
        val reservaGuardada = repositorioReserva.save(reservaCancelada)

        return ReservaResponse(reservaGuardada)
    }

    fun obtenerReservasPorCliente(clienteId: Long): List<ReservaResponse> {
        return repositorioReserva.findByClienteId(clienteId).map { ReservaResponse(it) }
    }

    fun obtenerReservaPorCodigo(codigo: String): ReservaResponse {
        val reserva = repositorioReserva.findByCodigoReserva(codigo)
            .orElseThrow { RecursoNoEncontradoException("Reserva no encontrada con código: $codigo") }
        return ReservaResponse(reserva)
    }

    fun obtenerTodasLasReservas(): List<ReservaResponse> {
        return repositorioReserva.findAll().map { ReservaResponse(it) }
    }

    fun obtenerReservasPorVuelo(vueloId: Long): List<ReservaResponse> {
        return repositorioReserva.encontrarReservasConfirmadasPorVueloId(vueloId).map { ReservaResponse(it) }
    }

    private fun generarCodigoReserva(): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..6)
            .map { caracteres.random() }
            .joinToString("")
    }
}
