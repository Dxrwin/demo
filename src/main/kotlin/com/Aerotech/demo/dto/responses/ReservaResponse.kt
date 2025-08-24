package com.Aerotech.demo.dto.responses

import VueloResponse
import com.Aerotech.demo.entities.EstadoReserva
import com.Aerotech.demo.entities.Reserva
import java.math.BigDecimal
import java.time.LocalDateTime

data class ReservaResponse(
    val id: Long,
    val codigoReserva: String,
    val vuelo: VueloResponse,
    val cliente: UsuarioResponse,
    val pasajeros: List<PasajeroResponse>,
    val fechaReserva: LocalDateTime,
    val estado: EstadoReserva,
    val montoTotal: BigDecimal
) {
    constructor(reserva: Reserva) : this(
        id = reserva.id!!,
        codigoReserva = reserva.codigoReserva,
        vuelo = VueloResponse(reserva.vuelo),
        cliente = UsuarioResponse(reserva.cliente),
        pasajeros = reserva.pasajeros.map { PasajeroResponse(it) },
        fechaReserva = reserva.fechaReserva,
        estado = reserva.estado,
        montoTotal = reserva.montoTotal
    )
}
