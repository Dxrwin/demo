package com.Aerotech.demo.dto.requests
import com.Aerotech.demo.entities.EstadoVuelo
import org.yaml.snakeyaml.constructor.Constructor

data class ActualizarEstadoVueloRequest(
    val estado: EstadoVuelo
)