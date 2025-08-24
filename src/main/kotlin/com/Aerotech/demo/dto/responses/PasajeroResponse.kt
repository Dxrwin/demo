package com.Aerotech.demo.dto.responses

import com.Aerotech.demo.entities.Pasajero

data class PasajeroResponse(
    val id: Long,
    val nombre: String,
    val documento: String,
    val edad: Int
) {
    constructor(pasajero: Pasajero) : this(
        id = pasajero.id!!,
        nombre = pasajero.nombre,
        documento = pasajero.documento,
        edad = pasajero.edad
    )
}