package com.Aerotech.demo.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "vuelos")
data class Vuelo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "numero_vuelo", unique = true, nullable = false)
    @NotBlank(message = "El número de vuelo es obligatorio")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Formato de vuelo inválido (ej: AT001)")
    val numeroVuelo: String,

    @Column(nullable = false)
    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 2, max = 100, message = "El origen debe tener entre 2 y 100 caracteres")
    val origen: String,

    @Column(nullable = false)
    @NotBlank(message = "El destino es obligatorio")
    @Size(min = 2, max = 100, message = "El destino debe tener entre 2 y 100 caracteres")
    val destino: String,

    @Column(name = "fecha_salida", nullable = false)
    @Future(message = "La fecha de salida debe ser futura")
    val fechaSalida: LocalDateTime,

    @Column(name = "fecha_llegada", nullable = false)
    val fechaLlegada: LocalDateTime,

    @Column(nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    val precio: BigDecimal,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val estado: EstadoVuelo = EstadoVuelo.PROGRAMADO,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeronave_id", nullable = false)
    val aeronave: Aeronave,

    @OneToMany(mappedBy = "vuelo", cascade = [CascadeType.ALL])
    @JsonIgnore
    val reservas: List<Reserva> = emptyList()
) {
    constructor() : this(
        id = null,
        numeroVuelo = "",
        origen = "",
        destino = "",
        fechaSalida = LocalDateTime.now(),
        fechaLlegada = LocalDateTime.now(),
        precio = BigDecimal.ZERO,
        estado = EstadoVuelo.PROGRAMADO,
        aeronave = Aeronave( modelo = "", capacidad = 0),
        reservas = emptyList()
    )

    fun obtenerAsientosDisponibles(): Int {
        val asientosReservados = reservas
            .filter { it.estado == EstadoReserva.CONFIRMADA }
            .sumOf { it.pasajeros.size }
        return aeronave.capacidad - asientosReservados
    }
}
