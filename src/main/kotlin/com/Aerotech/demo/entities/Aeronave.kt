package com.Aerotech.demo.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "aeronaves")
data class Aeronave(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 2, max = 50, message = "El modelo debe tener entre 2 y 50 caracteres")
    val modelo: String,

    @Column(nullable = false)
    @Positive(message = "La capacidad debe ser positiva")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 1000, message = "La capacidad máxima es 1000")
    val capacidad: Int,

    @OneToMany(mappedBy = "aeronave", cascade = [CascadeType.ALL])
    @JsonIgnore
    val vuelos: List<Vuelo> = emptyList()
)
{
    constructor() : this(
        id = null,
        modelo = "",
        capacidad = 0,
        vuelos = emptyList()
    )
}
