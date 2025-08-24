package com.Aerotech.demo.dto.responses


import com.Aerotech.demo.entities.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDateTime

data class AeronaveResponse(
    val id: Long,
    val model: String,
    val capacity: Int
) {
    constructor(aeronave : Aeronave ) : this(
        id = aeronave.id!!,
        model = aeronave.modelo,
        capacity = aeronave.capacidad
    )
}
