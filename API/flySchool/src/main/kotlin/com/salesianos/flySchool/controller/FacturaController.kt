package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.*
import com.salesianos.flySchool.security.jwt.MensajeError
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import com.salesianos.flySchool.service.UsuarioService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/factura")
class FacturaController(
        private val service: FacturaService,
        private val productoService: ProductoService,
        private val usuarioService: UsuarioService
) {
    @ApiOperation(
        value = "Listado de facuras",
        notes = "Se obtiene un lista con todas las facturas almacenadas"
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
    fun listado() : ResponseEntity<List<DtoFacturaAdmin>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.litado())
    }


    @ApiOperation(
        value = "Facuras por piloto",
        notes = "Se obtiene un lista con todas las facturas pertenecientes a un piloto"
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
    fun listadoUsuario(@AuthenticationPrincipal user: Usuario) : ResponseEntity<List<DtoFacturaCliente>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoUsuario(user))
    }


    @ApiOperation(
        value = "Creación de facuras",
        notes = "Se crea una factura a partir de los datos provenientes"
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
    fun crear(@PathVariable
              @ApiParam(value = "ID producto a comprar", required = true)
              id: UUID, @AuthenticationPrincipal user: Usuario) : ResponseEntity<DtoFacturaAdmin> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(id, user, productoService, usuarioService))


    }


}