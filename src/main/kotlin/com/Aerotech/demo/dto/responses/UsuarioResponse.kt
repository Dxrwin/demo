package com.Aerotech.demo.dto.responses

import com.Aerotech.demo.entities.RolUsuario
import com.Aerotech.demo.entities.Usuario
import java.time.LocalDateTime


data class UsuarioResponse(
    val id: Long,
    val correo: String,
    val nombre: String,
    val rol: RolUsuario,
    val fechaCreacion: LocalDateTime
) {
    constructor(usuario: Usuario) : this(
        id = usuario.id!!,
        correo = usuario.correo,
        nombre = usuario.nombre,
        rol = usuario.rol,
        fechaCreacion = usuario.fechaCreacion
    )
}
