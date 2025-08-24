import com.Aerotech.demo.dto.responses.AeronaveResponse
import com.Aerotech.demo.entities.EstadoVuelo
import com.Aerotech.demo.entities.Vuelo
import java.math.BigDecimal
import java.time.LocalDateTime

data class VueloResponse(
    val id: Long,
    val numeroVuelo: String,
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val fechaLlegada: LocalDateTime,
    val precio: BigDecimal,
    val estado: EstadoVuelo,
    val aeronave: AeronaveResponse,
    val asientosDisponibles: Int
) {
    constructor(vuelo: Vuelo) : this(
        id = vuelo.id!!,
        numeroVuelo = vuelo.numeroVuelo,
        origen = vuelo.origen,
        destino = vuelo.destino,
        fechaSalida = vuelo.fechaSalida,
        fechaLlegada = vuelo.fechaLlegada,
        precio = vuelo.precio,
        estado = vuelo.estado,
        aeronave = AeronaveResponse(vuelo.aeronave),
        asientosDisponibles = vuelo.obtenerAsientosDisponibles()
    )
}