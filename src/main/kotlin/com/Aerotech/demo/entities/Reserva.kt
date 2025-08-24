package com.Aerotech.demo.entities

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "codigo_reserva", unique = true, nullable = false)
    val codigoReserva: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vuelo_id", nullable = false)
    val vuelo: Vuelo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    val cliente: Usuario,

    @OneToMany(mappedBy = "reserva", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @Size(min = 1, message = "Debe haber al menos un pasajero")
    val pasajeros: List<Pasajero> = emptyList(),

    @Column(name = "fecha_reserva", nullable = false)
    val fechaReserva: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val estado: EstadoReserva = EstadoReserva.CONFIRMADA,

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    val montoTotal: BigDecimal
) {
    constructor() : this(
        id = null,
        codigoReserva = "",
        vuelo = Vuelo(),
        cliente = Usuario(correo = "", nombre = "", contrasena = ""),
        pasajeros = emptyList(),
        fechaReserva = LocalDateTime.now(),
        estado = EstadoReserva.CONFIRMADA,
        montoTotal = BigDecimal.ZERO
    )
}
