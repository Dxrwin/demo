package com.Aerotech.demo.repository

import com.Aerotech.demo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface RepositorioVuelo : JpaRepository<Vuelo, Long> {
    fun findByNumeroVuelo(numeroVuelo: String): Optional<Vuelo>
    fun findByEstado(estado: EstadoVuelo): List<Vuelo>

    @Query("""
        SELECT v FROM Vuelo v 
        WHERE LOWER(v.origen) = LOWER(:origen) 
        AND LOWER(v.destino) = LOWER(:destino) 
        AND DATE(v.fechaSalida) = DATE(:fechaSalida)
        AND v.estado = 'PROGRAMADO'
    """)
    fun encontrarVuelosDisponibles(
        @Param("origen") origen: String,
        @Param("destino") destino: String,
        @Param("fechaSalida") fechaSalida: LocalDateTime
    ): List<Vuelo>

    @Query("""
        SELECT v FROM Vuelo v 
        WHERE v.fechaSalida BETWEEN :fechaInicio AND :fechaFin
        ORDER BY v.fechaSalida ASC
    """)
    fun encontrarVuelosPorRangoFechas(
        @Param("fechaInicio") fechaInicio: LocalDateTime,
        @Param("fechaFin") fechaFin: LocalDateTime
    ): List<Vuelo>

    @Query("""
        SELECT v FROM Vuelo v
        WHERE v.origen = :origen
        ORDER BY v.fechaSalida DESC """)
    fun obtenerVuelosPorOrigen(
        @Param ("origen") origen: String
    ): List<Vuelo>

    @Query("""
        SELECT v FROM Vuelo v
        WHERE v.destino = :destino
        ORDER BY v.fechaSalida DESC """)
    fun obtenerVuelosPorDestino(@Param ("destino") destino: String): List<Vuelo>

    @Query("""
        SELECT v FROM Vuelo v 
        WHERE v.aeronave.id = :aeronaveId 
        ORDER BY v.fechaSalida DESC
    """)
    fun encontrarPorAeronaveId(@Param("aeronaveId") aeronaveId: Long): List<Vuelo>

    // Consulta para reportes - vuelos más reservados
    @Query("""
        SELECT v, COUNT(r) as cantidadReservas 
        FROM Vuelo v 
        LEFT JOIN v.reservas r 
        WHERE r.estado = 'CONFIRMADA'
        GROUP BY v 
        ORDER BY COUNT(r) DESC
    """)
    fun encontrarVuelosMasReservados(): List<Array<Any>>
}