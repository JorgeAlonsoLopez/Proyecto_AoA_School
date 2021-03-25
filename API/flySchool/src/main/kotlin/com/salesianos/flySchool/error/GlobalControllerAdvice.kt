package com.salesianos.flySchool.error

import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

/**
 * Clase que controla la gestión de las excepciones y su posterior representación
 */
@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {

    /**
     * Método encargado de capturar y manejar las excepciones del tipo EntityNotFoundException
     */
    @ExceptionHandler(value=[EntityNotFoundException::class])
    fun handleNotFoundException(ex: EntityNotFoundException) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiError(HttpStatus.NOT_FOUND, ex.message))

    /**
     * Método que permite modificar la información pertinente en caso de darse un error en la validación
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) : ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .body(
                ApiError(
                    status,
                    "Error de validación (handleMethodArgumentNotValid)",
                    ex.fieldErrors.map {
                        ApiValidationSubError(it.objectName, it.field, it.rejectedValue, it.defaultMessage)
                    }
                )
            )


    @ExceptionHandler(value=[ConstraintViolationException::class])
    fun handleConstraintViolation(ex : ConstraintViolationException) : ResponseEntity<ApiError> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiError(
                    HttpStatus.BAD_REQUEST,
                    "Error de validación (handleConstraintViolation)",
                    ex.constraintViolations
                        .map { ApiValidationSubError(
                            it.rootBeanClass.simpleName, (it.propertyPath as PathImpl).leafNode.asString(), it.invalidValue, it.message)
                        }
                )
            )


    /**
     * Método encargado de la customización de las excepciones. Es en este método donde inertamos la clase ApiError
     * para encapsular los mensajes
     * @see ApiError
     */
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(status, ex.message)
        return ResponseEntity.status(status).body(apiError)
    }


}