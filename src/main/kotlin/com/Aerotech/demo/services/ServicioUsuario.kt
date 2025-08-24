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
class ServicioUsuario(
    private val repositorioUsuario: RepositorioUsuario
) {

    fun crearUsuario(request: CrearUsuarioRequest): UsuarioResponse {
        if (repositorioUsuario.existsByCorreo(request.email)) {
            throw ReglaNegocioException("Ya existe un usuario con este correo electrónico")
        }

        val usuario = Usuario(
            correo = request.email,
            nombre = request.name,
            contrasena = request.password, // En producción debería estar encriptado
            rol = request.role
        )

        val usuarioGuardado = repositorioUsuario.save(usuario)
        return UsuarioResponse(usuarioGuardado)
    }

    fun encontrarUsuarioPorCorreo(correo: String): UsuarioResponse {
        val usuario = repositorioUsuario.findByCorreo(correo)
            .orElseThrow { RecursoNoEncontradoException("Usuario no encontrado con correo: $correo") }
        return UsuarioResponse(usuario)
    }

    fun obtenerTodosLosUsuarios(): List<UsuarioResponse> {
        return repositorioUsuario.findAll().map { UsuarioResponse(it) }
    }

    fun obtenerUsuariosPorRol(rol: RolUsuario): List<UsuarioResponse> {
        return repositorioUsuario.findByRol(rol).map { UsuarioResponse(it) }
    }

    fun autenticarUsuario(request: IniciarSesionRequest): UsuarioResponse {
        val usuario = repositorioUsuario.findByCorreo(request.correo)
            .orElseThrow { NoAutorizadoException("Credenciales inválidas") }

        if (usuario.contrasena != request.contrasena) { // En producción usar BCrypt
            throw NoAutorizadoException("Credenciales inválidas")
        }

        return UsuarioResponse(usuario)
    }
}