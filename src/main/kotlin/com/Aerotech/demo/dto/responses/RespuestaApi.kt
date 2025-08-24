package com.Aerotech.demo.dto.responses

// DTOs de respuesta genérica
data class RespuestaApi<T>(
    val exitoso: Boolean,
    val mensaje: String,
    val datos: T? = null,
    val errores: List<String>? = null
) {
    companion object {
        fun <T> exitoso(datos: T, mensaje: String = "Operación exitosa"): RespuestaApi<T> {
            return RespuestaApi(exitoso = true, mensaje = mensaje, datos = datos)
        }

        fun <T> error(mensaje: String, errores: List<String>? = null): RespuestaApi<T> {
            return RespuestaApi(exitoso = false, mensaje = mensaje, errores = errores)
        }
    }
}