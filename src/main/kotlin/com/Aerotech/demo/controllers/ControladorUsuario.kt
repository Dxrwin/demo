package com.Aerotech.demo.controllers


import com.Aerotech.demo.dto.responses.RespuestaApi
import com.Aerotech.demo.dto.responses.UsuarioResponse
import com.Aerotech.demo.entities.*
import com.Aerotech.demo.services.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
class ControladorUsuario(
    private val servicioUsuario: ServicioUsuario
) {

    @GetMapping("/todos")
    @Operation(summary = "Obtener todos los usuarios", description = "Solo para administradores")
    fun obtenerTodosLosUsuarios(): ResponseEntity<RespuestaApi<List<UsuarioResponse>>> {
        val usuarios = servicioUsuario.obtenerTodosLosUsuarios()
        return ResponseEntity.ok(RespuestaApi.exitoso(usuarios))
    }

    @GetMapping("/rol/{rol}")
    @Operation(summary = "Obtener usuarios por rol")
    fun obtenerUsuariosPorRol(@PathVariable rol: RolUsuario): ResponseEntity<RespuestaApi<List<UsuarioResponse>>> {
        val usuarios = servicioUsuario.obtenerUsuariosPorRol(rol)
        return ResponseEntity.ok(RespuestaApi.exitoso(usuarios))
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo electrónico")
    fun obtenerUsuarioPorCorreo(@PathVariable correo: String): ResponseEntity<RespuestaApi<UsuarioResponse>> {
        val usuario = servicioUsuario.encontrarUsuarioPorCorreo(correo)
        return ResponseEntity.ok(RespuestaApi.exitoso(usuario))
    }
}