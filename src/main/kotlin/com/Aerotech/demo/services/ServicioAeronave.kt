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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Service
@Transactional
class ServicioAeronave(
    private val repositorioAeronave: RepositorioAeronave
) {

    fun obtenerAeronavesDisponiblesHoy(): List<AeronaveResponse> {
        val hoy = LocalDate.now()
        val inicioDia = hoy.atStartOfDay()
        val finDia = hoy.atTime(LocalTime.MAX)

        val disponibles = repositorioAeronave.encontrarAeronavesDisponibles(inicioDia, finDia)
        return disponibles.map { AeronaveResponse(it) }
    }

    fun crearAeronave(request: CrearAeronaveRequest): AeronaveResponse {
        if (repositorioAeronave.existsByModelo(request.modelo)) {
            throw ReglaNegocioException("Ya existe una aeronave con este modelo")
        }

        val aeronave = Aeronave(
            modelo = request.modelo,
            capacidad = request.capacidad
        )

        val aeronaveGuardada = repositorioAeronave.save(aeronave)
        return AeronaveResponse(aeronaveGuardada)
    }

    fun obtenerTodasLasAeronaves(): List<AeronaveResponse> {
        return repositorioAeronave.findAll().map { AeronaveResponse(it) }
    }

    fun obtenerAeronavePorId(id: Long): AeronaveResponse {
        val aeronave = repositorioAeronave.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Aeronave no encontrada con ID: $id") }
        return AeronaveResponse(aeronave)
    }

    fun obtenerAeronavesPorCapacidadMinima(capacidadMinima: Int): List<AeronaveResponse> {
        return repositorioAeronave.encontrarPorCapacidadMinima(capacidadMinima).map { AeronaveResponse(it) }
    }
}