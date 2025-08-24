package com.Aerotech.demo.Exception

import com.Aerotech.demo.dto.responses.RespuestaApi
import com.Aerotech.demo.services.ReglaNegocioException
import com.Aerotech.demo.services.RecursoNoEncontradoException
import com.Aerotech.demo.services.NoAutorizadoException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class ManejadorGlobalExcepciones {

        // Manejo de RecursoNoEncontradoException
        @ExceptionHandler(RecursoNoEncontradoException::class)
        fun manejarRecursoNoEncontrado(
            ex: RecursoNoEncontradoException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = ex.message ?: "Recurso no encontrado",
                errores = listOf(ex.message ?: "Recurso no encontrado")
            )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta)
        }


        // Manejo de ReglaNegocioException
        @ExceptionHandler(ReglaNegocioException::class)
        fun manejarReglaNegocio(
            ex: ReglaNegocioException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = ex.message ?: "Regla de negocio violada",
                errores = listOf(ex.message ?: "Regla de negocio violada")
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta)
        }


        // Manejo de NoAutorizadoException
        @ExceptionHandler(NoAutorizadoException::class)
        fun manejarNoAutorizado(
            ex: NoAutorizadoException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = ex.message ?: "No autorizado",
                errores = listOf(ex.message ?: "No autorizado")
            )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta)
        }


        // Manejo de MethodArgumentNotValidException para errores de validación
        @ExceptionHandler(MethodArgumentNotValidException::class)
        fun manejarExcepcionesValidacion(
            ex: MethodArgumentNotValidException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val errores = ex.bindingResult.fieldErrors.map { error ->
                "${error.field}: ${error.defaultMessage}"
            }

            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = "Error de validación",
                errores = errores
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta)
        }



        // Manejo de IllegalArgumentException
        @ExceptionHandler(IllegalArgumentException::class)
        fun manejarArgumentoIlegal(
            ex: IllegalArgumentException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = "Argumento inválido: ${ex.message}",
                errores = listOf(ex.message ?: "Argumento inválido")
            )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta)
        }


        // Manejo de excepciones genéricas
        @ExceptionHandler(Exception::class)
        fun manejarExcepcionGenerica(
            ex: Exception,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = "Error interno del servidor",
                errores = listOf("Ha ocurrido un error inesperado: ${ex.message}")
            )
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta)
        }



        // Manejo de RuntimeException
        @ExceptionHandler(RuntimeException::class)
        fun manejarExcepcionTiempoEjecucion(
            ex: RuntimeException,
            request: WebRequest
        ): ResponseEntity<RespuestaApi<Nothing>> {
            val respuesta = RespuestaApi.error<Nothing>(
                mensaje = "Error en tiempo de ejecución",
                errores = listOf(ex.message ?: "Error en tiempo de ejecución")
            )
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta)
        }


}
