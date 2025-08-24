package com.Aerotech.demo.controllers

import VueloResponse
import com.Aerotech.demo.dto.requests.*
import com.Aerotech.demo.dto.responses.*
import com.Aerotech.demo.entities.EstadoVuelo
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
@RequestMapping("/vuelos")
@Tag(name = "Vuelos", description = "Gestión de vuelos")
class ControladorVuelo(
    private val servicioVuelo: ServicioVuelo
) {

    @PostMapping
    @Operation(summary = "Crear nuevo vuelo", description = "Solo para empleados y administradores")
    fun crearVuelo(@Valid @RequestBody request: CrearVueloRequest): ResponseEntity<RespuestaApi<VueloResponse>> {
        val vuelo = servicioVuelo.crearVuelo(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(RespuestaApi.exitoso(vuelo, "Vuelo creado exitosamente"))
    }

    @GetMapping
    @Operation(summary = "Obtener todos los vuelos")
    fun obtenerTodosLosVuelos(): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.obtenerTodosLosVuelos()
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vuelo por ID")
    fun obtenerVueloPorId(@PathVariable id: Long): ResponseEntity<RespuestaApi<VueloResponse>> {
        val vuelo = servicioVuelo.obtenerVueloPorId(id)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelo))
    }

    @PostMapping("/buscar")
    @Operation(summary = "Buscar vuelos disponibles")
    fun buscarVuelos(@Valid @RequestBody request: BuscarVuelosRequest): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.buscarVuelos(request)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos, "Búsqueda completada"))
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener vuelos por estado")
    fun obtenerVuelosPorEstado(@PathVariable estado: EstadoVuelo): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.obtenerVuelosPorEstado(estado)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos))
    }


    @PutMapping("actualizar-estado/{id}/{request}")
    @Operation(summary = "Actualizar estado de vuelo", description = "Solo para empleados y administradores")
    fun actualizarEstadoVuelo(
        @PathVariable id: Long,
        @PathVariable request: EstadoVuelo
    ): ResponseEntity<RespuestaApi<VueloResponse>> {

        println("Actualizando estado del vuelo con ID: $id a ${request}")
        println("Request recibido: $request")
        val vuelo = servicioVuelo.actualizarEstadoVuelo(id, request)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelo, "Estado de vuelo actualizado"))
    }

    @GetMapping("/rango-fechas")
    @Operation(summary = "Obtener vuelos por rango de fechas")
    fun obtenerVuelosPorRangoFechas(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) fechaInicio: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) fechaFin: LocalDateTime
    ): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.obtenerVuelosPorRangoFechas(fechaInicio, fechaFin)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos))
    }

    @GetMapping("/buscar-origen/{origen}")
    @Operation(summary = "Obtener vuelos por ciudad de origen")
    fun obtenerVuelosPorOrigen(
        @PathVariable origen: String
    ): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.obtenerVuelosPorOrigen(origen)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos))
    }

    @GetMapping("/buscar-destino/{destino}")
    @Operation(summary = "Obtener vuelos por ciudad de destino")
    fun obtenerVuelosPorDestino(
        @PathVariable destino: String
    ): ResponseEntity<RespuestaApi<List<VueloResponse>>> {
        val vuelos = servicioVuelo.obtenerVuelosPorDestino(destino)
        return ResponseEntity.ok(RespuestaApi.exitoso(vuelos))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de vuelo", description = "Solo para empleados y administradores")
    fun actualizarVuelo(
        @PathVariable id: Long,
        @Valid @RequestBody request: ActualizarVueloRequest
    ): ResponseEntity<RespuestaApi<VueloResponse>> {
        val vueloActualizado = servicioVuelo.actualizarVuelo(id, request)
        return ResponseEntity.ok(RespuestaApi.exitoso(vueloActualizado, "Vuelo actualizado"))
    }

    @GetMapping("/{id}/pasajeros")
    @Operation(summary = "Ver información de pasajeros por vuelo")
    fun verPasajerosPorVuelo(
        @PathVariable id: Long
    ): ResponseEntity<RespuestaApi<List<PasajeroResponse>>> {
        val pasajeros = servicioVuelo.obtenerPasajerosPorVuelo(id)
        return ResponseEntity.ok(RespuestaApi.exitoso(pasajeros, "Pasajeros del vuelo"))
    }

}