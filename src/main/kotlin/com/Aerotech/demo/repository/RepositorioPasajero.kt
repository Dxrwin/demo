package com.Aerotech.demo.repository

import com.Aerotech.demo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface RepositorioPasajero : JpaRepository<Pasajero, Long> {
    fun findByDocumento(documento: String): Optional<Pasajero>

    @Query("""
        SELECT p FROM Pasajero p 
        WHERE p.reserva.vuelo.id = :vueloId
        AND p.reserva.estado = 'CONFIRMADA'
    """)
    fun encontrarPasajerosPorVueloId(@Param("vueloId") vueloId: Long): List<Pasajero>

    @Query("""
        SELECT p FROM Pasajero p 
        WHERE p.reserva.id = :reservaId
    """)
    fun encontrarPorReservaId(@Param("reservaId") reservaId: Long): List<Pasajero>

    @Query("""
        SELECT COUNT(p) FROM Pasajero p 
        WHERE p.reserva.vuelo.id = :vueloId
        AND p.reserva.estado = 'CONFIRMADA'
    """)
    fun contarPasajerosPorVueloId(@Param("vueloId") vueloId: Long): Long
}