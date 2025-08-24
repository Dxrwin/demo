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
class ServicioPasajero(
    private val repositorioPasajero: RepositorioPasajero
) {

    fun obtenerPasajerosPorVuelo(vueloId: Long): List<PasajeroResponse> {
        return repositorioPasajero.encontrarPasajerosPorVueloId(vueloId).map { PasajeroResponse(it) }
    }

    fun obtenerPasajerosPorReserva(reservaId: Long): List<PasajeroResponse> {
        return repositorioPasajero.encontrarPorReservaId(reservaId).map { PasajeroResponse(it) }
    }

    fun obtenerPasajeroPorId(id: Long): PasajeroResponse {
        val pasajero = repositorioPasajero.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Pasajero no encontrado con ID: $id") }
        return PasajeroResponse(pasajero)
    }
}