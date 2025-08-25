package com.Aerotech.demo.repository

import com.Aerotech.demo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface RepositorioAeronave : JpaRepository<Aeronave, Long> {
    fun findByModelo(modelo: String): Optional<Aeronave>
    fun existsByModelo(modelo: String): Boolean

    @Query("""
    SELECT a 
    FROM Aeronave a 
    WHERE a.id NOT IN (
        SELECT v.aeronave.id 
        FROM Vuelo v 
        WHERE v.fechaSalida BETWEEN :inicioDia AND :finDia
    )
""")
    fun encontrarAeronavesDisponibles(
        @Param("inicioDia") inicioDia: LocalDateTime,
        @Param("finDia") finDia: LocalDateTime
    ): List<Aeronave>

    @Query("SELECT a FROM Aeronave a WHERE a.capacidad >= :capacidadMinima")
    fun encontrarPorCapacidadMinima(@Param("capacidadMinima") capacidadMinima: Int): List<Aeronave>
}