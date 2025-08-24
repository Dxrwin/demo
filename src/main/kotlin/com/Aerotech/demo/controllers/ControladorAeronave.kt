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
@RequestMapping("/aeronaves")
@Tag(name = "Aeronaves", description = "Gestión de aeronaves")
class ControladorAeronave(
    private val servicioAeronave: ServicioAeronave
) {

    @PostMapping
    @Operation(summary = "Crear nueva aeronave", description = "Solo para administradores")
    fun crearAeronave(@Valid @RequestBody request: CrearAeronaveRequest): ResponseEntity<RespuestaApi<AeronaveResponse>> {
        val aeronave = servicioAeronave.crearAeronave(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(RespuestaApi.exitoso(aeronave, "Aeronave creada exitosamente"))
    }

    @GetMapping
    @Operation(summary = "Obtener todas las aeronaves")
    fun obtenerTodasLasAeronaves(): ResponseEntity<RespuestaApi<List<AeronaveResponse>>> {
        val aeronaves = servicioAeronave.obtenerTodasLasAeronaves()
        return ResponseEntity.ok(RespuestaApi.exitoso(aeronaves))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener aeronave por ID")
    fun obtenerAeronavePorId(@PathVariable id: Long): ResponseEntity<RespuestaApi<AeronaveResponse>> {
        val aeronave = servicioAeronave.obtenerAeronavePorId(id)
        return ResponseEntity.ok(RespuestaApi.exitoso(aeronave))
    }

    @GetMapping("/capacidad/{capacidadMinima}")
    @Operation(summary = "Obtener aeronaves con capacidad mínima")
    fun obtenerAeronavesPorCapacidadMinima(@PathVariable capacidadMinima: Int): ResponseEntity<RespuestaApi<List<AeronaveResponse>>> {
        val aeronaves = servicioAeronave.obtenerAeronavesPorCapacidadMinima(capacidadMinima)
        return ResponseEntity.ok(RespuestaApi.exitoso(aeronaves))
    }
}