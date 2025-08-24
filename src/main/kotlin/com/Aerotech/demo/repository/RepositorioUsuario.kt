package com.Aerotech.demo.repository

import com.Aerotech.demo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface RepositorioUsuario : JpaRepository<Usuario, Long> {
    fun findByCorreo(correo: String): Optional<Usuario>
    fun findByRol(rol: RolUsuario): List<Usuario>
    fun existsByCorreo(correo: String): Boolean

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol = :rol")
    fun contarPorRol(@Param("rol") rol: RolUsuario): Long
}