package com.Aerotech.demo.services

import VueloResponse
import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.PasajeroResponse
import com.Aerotech.demo.entities.*
import com.Aerotech.demo.repository.*
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ServicioVuelo(
    private val repositorioVuelo: RepositorioVuelo,
    private val repositorioAeronave: RepositorioAeronave
) {

    fun crearVuelo(request: CrearVueloRequest): VueloResponse {
        // Validaciones de reglas de negocio
        if (request.origen.equals(request.destino, ignoreCase = true)) {
            throw ReglaNegocioException("El origen y destino deben ser diferentes")
        }

        if (request.fechaLlegada.isBefore(request.fechaSalida)) {
            throw ReglaNegocioException("La fecha de llegada debe ser posterior a la fecha de salida")
        }

        if (repositorioVuelo.findByNumeroVuelo(request.numeroVuelo).isPresent) {
            throw ReglaNegocioException("Ya existe un vuelo con este número")
        }

        val aeronave = repositorioAeronave.findById(request.aeronaveId)
            .orElseThrow { RecursoNoEncontradoException("Aeronave no encontrada con ID: ${request.aeronaveId}") }

        val vuelo = Vuelo(
            numeroVuelo = request.numeroVuelo,
            origen = request.origen,
            destino = request.destino,
            fechaSalida = request.fechaSalida,
            fechaLlegada = request.fechaLlegada,
            precio = request.precio,
            aeronave = aeronave
        )

        val vueloGuardado = repositorioVuelo.save(vuelo)
        return VueloResponse(vueloGuardado)
    }

    fun actualizarEstadoVuelo(vueloId: Long, request : EstadoVuelo): VueloResponse {
        val vuelo = repositorioVuelo.findById(vueloId)
            .orElseThrow { RecursoNoEncontradoException("Vuelo no encontrado con ID: $vueloId") }

        val vueloActualizado = vuelo.copy(estado = request)
        val vueloGuardado = repositorioVuelo.save(vueloActualizado)
        return VueloResponse(vueloGuardado)
    }

    fun actualizarVuelo(id: Long, request: ActualizarVueloRequest): VueloResponse {
        val vuelo = repositorioVuelo.findById(id)
            .orElseThrow { EntityNotFoundException("Vuelo no encontrado con id: $id") }

        val vueloActualizado = vuelo.copy(
            origen = request.origen,
            destino = request.destino,
            fechaSalida = request.fechaSalida,
            fechaLlegada = request.fechaLlegada,
            precio = request.precio
        )

        val vueloGuardado = repositorioVuelo.save(vueloActualizado)
        return VueloResponse(vueloGuardado)
    }

    fun obtenerPasajerosPorVuelo(id: Long): List<PasajeroResponse> {
        val vuelo = repositorioVuelo.findById(id)
            .orElseThrow { EntityNotFoundException("Vuelo no encontrado con id: $id") }
        return vuelo.reservas
            .flatMap { it.pasajeros }
            .map { PasajeroResponse(it) }
    }

    fun buscarVuelos(request: BuscarVuelosRequest): List<VueloResponse> {
        return repositorioVuelo.encontrarVuelosDisponibles(
            request.origen,
            request.destino,
            request.fechaSalida
        ).map { VueloResponse(it) }
    }

    fun obtenerTodosLosVuelos(): List<VueloResponse> {
        return repositorioVuelo.findAll().map { VueloResponse(it) }
    }

    fun obtenerVueloPorId(id: Long): VueloResponse {
        val vuelo = repositorioVuelo.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Vuelo no encontrado con ID: $id") }
        return VueloResponse(vuelo)
    }

    fun obtenerVuelosPorEstado(estado: EstadoVuelo): List<VueloResponse> {
        return repositorioVuelo.findByEstado(estado).map { VueloResponse(it) }
    }

    fun obtenerVuelosPorRangoFechas(fechaInicio: LocalDateTime, fechaFin: LocalDateTime): List<VueloResponse> {
        return repositorioVuelo.encontrarVuelosPorRangoFechas(fechaInicio, fechaFin).map { VueloResponse(it) }
    }


    fun obtenerVuelosPorOrigen(origen : String): List<VueloResponse> {
        return repositorioVuelo.obtenerVuelosPorOrigen(origen).map { VueloResponse(it) }
    }
    fun obtenerVuelosPorDestino(destino : String): List<VueloResponse> {
        return repositorioVuelo.obtenerVuelosPorDestino(destino).map { VueloResponse(it) }
    }


}