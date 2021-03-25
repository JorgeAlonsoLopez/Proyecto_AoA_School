package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.error.ApiError
import com.salesianos.flySchool.security.jwt.MensajeError
import com.salesianos.flySchool.service.*
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/registro")
class RegistroController(
        private val service: RegistroService,
        private val aeronaveService: AeronaveService,
        private val pilotoService: PilotoService
) {

    @ApiOperation(
        value = "Todos los registros",
        notes = "Se listan todos los registros almacenados"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoRegistro>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }


    @ApiOperation(
        value = "Registros por usuario",
        notes = "Se listan todos los registros pertenecientes a un piloto"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("/user")
    fun listadoUsuario(@AuthenticationPrincipal user: Usuario) : ResponseEntity<List<DtoRegistro>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoUsuario(user))
    }


    @ApiOperation(
        value = "Creación de registro",
        notes = "Se crea un registro con los datos proporcionados"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 201, message = "Created", response = DtoAeronaveResp::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class)
        ]
    )
    @PostMapping("/{id}")
    fun crear(@RequestBody
              @ApiParam(value = "Datos provenientes del registro", required = true)
              nueva: DtoRegistroForm, @PathVariable
                @ApiParam(value = "Id de la aeronave que se quiere indicar en el registro", required = true)
                id: UUID, @AuthenticationPrincipal user: Usuario) : ResponseEntity<DtoRegistro> {
        var resl = service.crear(nueva, id, user, aeronaveService, pilotoService)
        if(resl != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(resl!!)
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

}