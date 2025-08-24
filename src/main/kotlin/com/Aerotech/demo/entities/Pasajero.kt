package com.Aerotech.demo.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "pasajeros")
data class Pasajero(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    val nombre: String,

    @Column(nullable = false)
    @NotBlank(message = "El documento es obligatorio")
    @Size(min = 5, max = 20, message = "El documento debe tener entre 5 y 20 caracteres")
    val documento: String,

    @Column(nullable = false)
    @Min(value = 0, message = "La edad debe ser positiva")
    @Max(value = 120, message = "La edad máxima es 120")
    val edad: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id")
    @JsonIgnore
    val reserva: Reserva? = null
) {
    constructor() : this(
        id = null,
        nombre = "",
        documento = "",
        edad = 0,
        reserva = null
    )
}