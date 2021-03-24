package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.error.ApiError
import com.salesianos.flySchool.security.jwt.MensajeError
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/producto")
class ProductoController(
    private val service: ProductoService,
    private val facturaService: FacturaService
) {
    @ApiOperation(
        value = "Crear producto",
        notes = "Creación de un producto en base a los datos proporcionados"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 201, message = "Created", response = DtoAeronaveResp::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class)
        ]
    )
    @PostMapping("/")
    fun crear(@Valid @RequestBody
              @ApiParam(value = "Datos del producto", required = true)
              nueva: DtoProductoForm) : ResponseEntity<DtoProductoEspecf> {
       return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(nueva))
    }


    @ApiOperation(
        value = "Editar producto",
        notes = "Edición de los datos un producto, determinado por el ID, con los datos proporcionados"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody
               @ApiParam(value = "Datos del producto", required = true)
               editada: DtoProductoForm, @PathVariable
                @ApiParam(value = "ID del producto a editar", required = true)
                id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(editada, id))
    }


    @ApiOperation(
        value = "Cambiar estado de producto",
        notes = "Cambiar el estado de un producto, entre estar dado de alta o baja"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @PutMapping("/{id}/est")
    fun cambiarEstado(@PathVariable
                      @ApiParam(value = "ID del producto a modificar", required = true)
                      id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id))
    }


    @ApiOperation(
        value = "Eliminar producto",
        notes = "Eliminar un producto concreto, buscándolo por el ID que se proporciona. En caso de " +
                "que dicho producto haya sido comprado, no podrá ser eliminado"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class),
            ApiResponse(code = 400, message = "El producto ya ha sido comprado con anterioridad, no se puede eliminar", response = ApiError::class),
        ]
    )
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable
                 @ApiParam(value = "ID del producto a eliminar", required = true)
                 id: UUID): ResponseEntity<Unit> {
        var resl = service.eliminar(id, facturaService)
        if(resl != 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }


    @ApiOperation(
        value = "Listado de producto",
        notes = "Se obtiene el listado de todos los productos"
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
    fun listado() : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }


    @ApiOperation(
        value = "Listado de productos por estado",
        notes = "Se obtiene el listado de todos los productos cuyo valos de alta corresponda con el proporcionado"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("/alta/{licencia}")
    fun listadoAlta(@PathVariable
                    @ApiParam(value = "Valor del tipo de producto a buscar", required = true)
                    tipo: Boolean) : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoAlta(tipo))
    }


    @ApiOperation(
        value = "Búsqueda de producto",
        notes = "Se busca un determinado producto por el ID proporcionado"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("/{id}")
    fun productoId(@PathVariable
                   @ApiParam(value = "ID del producto a buscar", required = true)
                   id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.productoId(id))
    }




}