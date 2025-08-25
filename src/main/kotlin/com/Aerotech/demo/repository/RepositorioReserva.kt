package com.Aerotech.demo.repository

import com.Aerotech.demo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Repository
interface RepositorioReserva : JpaRepository<Reserva, Long> {
    fun findByCodigoReserva(codigoReserva: String): Optional<Reserva>
    fun findByClienteId(clienteId: Long): List<Reserva>
    fun findByEstado(estado: EstadoReserva): List<Reserva>


    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.estado = 'COMPLETADA'")
    fun contarReservasCompletadas(): Long

    @Query("""
    SELECT COALESCE(SUM(r.montoTotal), 0)
    FROM Reserva r
    WHERE r.fechaReserva BETWEEN :inicioMes AND :finMes
""")
    fun calcularGananciasMesActual(
        @Param("inicioMes") inicioMes: LocalDateTime,
        @Param("finMes") finMes: LocalDateTime
    ): BigDecimal

    @Query(""" 
        SELECT COUNT(DISTINCT r.vuelo.id) 
        FROM Reserva r 
        WHERE r.estado = :estado
    """)
    fun countDistinctByEstado(@Param("estado") estado: EstadoReserva): Long

    @Query("""
        SELECT r FROM Reserva r 
        WHERE r.vuelo.id = :vueloId 
        AND r.estado = 'CONFIRMADA'
    """)
    fun encontrarReservasConfirmadasPorVueloId(@Param("vueloId") vueloId: Long): List<Reserva>

    @Query("""
        SELECT r FROM Reserva r 
        WHERE r.cliente.id = :clienteId 
        AND r.estado = :estado
        ORDER BY r.fechaReserva DESC
    """)
    fun encontrarPorClienteIdYEstado(
        @Param("clienteId") clienteId: Long,
        @Param("estado") estado: EstadoReserva
    ): List<Reserva>

    @Query("""
        SELECT SUM(r.montoTotal) 
        FROM Reserva r 
        WHERE r.fechaReserva BETWEEN :fechaInicio AND :fechaFin
        AND r.estado = 'CONFIRMADA'
    """)
    fun obtenerIngresosTotalesPorPeriodo(
        @Param("fechaInicio") fechaInicio: LocalDateTime,
        @Param("fechaFin") fechaFin: LocalDateTime
    ): Double?

    @Query("""
        SELECT COUNT(r) 
        FROM Reserva r 
        WHERE r.vuelo.id = :vueloId 
        AND r.estado = 'CONFIRMADA'
    """)
    fun contarReservasConfirmadasPorVueloId(@Param("vueloId") vueloId: Long): Long

    // Verificar si se puede cancelar (2 horas antes del vuelo)
    @Query("""
        SELECT r FROM Reserva r 
        WHERE r.id = :reservaId 
        AND r.vuelo.fechaSalida > :fechaActualMas2Horas
        AND r.estado = 'CONFIRMADA'
    """)
    fun encontrarReservaCancelable(
        @Param("reservaId") reservaId: Long,
        @Param("fechaActualMas2Horas") fechaActualMas2Horas: LocalDateTime
    ): Optional<Reserva>
}
