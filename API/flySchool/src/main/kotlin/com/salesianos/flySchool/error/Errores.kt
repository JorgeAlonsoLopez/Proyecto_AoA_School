package com.salesianos.flySchool.error

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

/**
 * Clase encargada de mostrar mesajes de error customizados a la hora de tratar excepciones
 */
data class ApiError(
    val estado: HttpStatus,
    val mensaje: String?,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val subErrores: List<out ApiSubError>? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    val fecha: LocalDateTime = LocalDateTime.now(),
)

/**
 * Clase abstracta que aglutinará los errores originarios de la excepción, para mostrarlos todos juntos
 */
open abstract class ApiSubError

/**
 * Clase que hereda de ApiSubError, mostrando una información básica sobre el error ocurrido
 */
data class ApiValidationSubError(
    val objeto : String,
    val campo : String,
    val valorRechazado : Any?,
    val mensaje : String?
) : ApiSubError()