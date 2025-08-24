package com.Aerotech.demo.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo es obligatorio")
    val correo: String,

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    val nombre: String,

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @JsonIgnore
    val contrasena: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val rol: RolUsuario = RolUsuario.CLIENTE,

    @Column(name = "fecha_creacion", nullable = false)
    val fechaCreacion: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "cliente", cascade = [CascadeType.ALL])
    @JsonIgnore
    val reservas: List<Reserva> = emptyList()
) {
    constructor() : this(
        id = null,
        correo = "",
        nombre = "",
        contrasena = "",
        rol = RolUsuario.CLIENTE,
        fechaCreacion = LocalDateTime.now(),
        reservas = emptyList()
    )
}