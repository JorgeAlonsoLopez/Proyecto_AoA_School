package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.error.ApiError
import com.salesianos.flySchool.security.jwt.MensajeError
import com.salesianos.flySchool.service.AeronaveService
import com.salesianos.flySchool.service.FotoAeronaveServicio
import com.salesianos.flySchool.service.RegistroService
import com.salesianos.flySchool.upload.ImgurStorageService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/aeronave")
class AeronaveController(
    private val service: AeronaveService,
    private val servicioFoto: FotoAeronaveServicio,
    private val imgurStorageService: ImgurStorageService,
    private val registroService:RegistroService
) {

    @ApiOperation(
        value = "Creación de una nueva aeronave",
        notes = "Se aportan los datos provenientes del formulario de creación de la aeronave"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 201, message = "Created", response = DtoAeronaveSinFoto::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Bad credentials", response = MensajeError::class)
        ]
    )
    @PostMapping("/")
    fun crear(@Valid @RequestBody
              @ApiParam(value = "Los datos del registro", required = true)
              nueva:DtoAeronaveForm): ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(nueva))
    }

    @ApiOperation(
        value = "Añadir una foto",
        notes = "Se sube una foto al servicio y se asocia con la aeronave"
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
    @PostMapping("/{id}")
    fun addFoto(@PathVariable
                @ApiParam(value = "Id de la aeronave a la que se le va a asignar la foto", required = true)
                id: UUID, @RequestPart("file")
                @ApiParam(value = "Foto que se va a subir al servicio", required = true)
                file: MultipartFile) : ResponseEntity<DtoAeronaveResp> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addFoto(id, file, servicioFoto))
    }


    @ApiOperation(
        value = "Eliminar una aeronave y su foto",
        notes = "Se elimina la entidad correspondiente al ID proporcionato y su imagen asociada. " +
                "En caso de existir un registro de vuelo con dicha aeronave, no podrá eliminarse"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 204, message = "No content"),
            ApiResponse(code = 400, message = "No puede eliminarse la aeronave", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @DeleteMapping("/{id}/{hash}")
    fun delete(@PathVariable
               @ApiParam(value = "Hash de eliminación perteneciente a la foto", required = true)
               hash: String, @PathVariable
                @ApiParam(value = "Id de la aeronave a la que se le va a eliminar", required = true)
                id: UUID): ResponseEntity<Any> {
        var rest = service.delete(hash, id, imgurStorageService,registroService, servicioFoto)
        if(rest){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }


    @ApiOperation(
        value = "Eliminar una foto",
        notes = "Se elimina la imagen asociada a la entidad cuyo ID se ha proporcionado"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 204, message = "No content"),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @DeleteMapping("/foto/{id}/{hash}")
    fun deleteFoto(@PathVariable
                    @ApiParam(value = "Hash de eliminación perteneciente a la foto", required = true)
                    hash: String, @PathVariable
                    @ApiParam(value = "Id de la aeronave a la que se le va a eliminar", required = true)
                    id: UUID) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteFoto(hash, id, imgurStorageService,registroService, servicioFoto))
    }


    @ApiOperation(
        value = "Listado de todas las aeronaves",
        notes = "Se obtiene un listado de todas las aeronaves"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class)
        ]
    )
    @GetMapping("/")
    fun listado(): ResponseEntity<List<DtoAeronaveResp>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }


    @ApiOperation(
        value = "Listado de aeronaves de alta",
        notes = "Se obtiene un listado de todas las aeronaves cuya porpiedad alta sea true"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoAeronaveResp::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class)
        ]
    )
    @GetMapping("/alta")
    fun listadoAlta(): ResponseEntity<List<DtoAeronaveResp>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoAlta())
    }


    @ApiOperation(
        value = "Buscar una aeronave",
        notes = "Se busca una aeronave en particular proporcionando el ID de esta"
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
    @GetMapping("/{id}")
    fun aeronaveId(@PathVariable
                   @ApiParam(value = "Id de la aeronave que se quiere buscar", required = true)
                   id: UUID): ResponseEntity<DtoAeronaveResp> {
        return ResponseEntity.status(HttpStatus.OK).body(service.aeronaveId(id))
    }


    @ApiOperation(
        value = "Editar una aeronave",
        notes = "Se busca una aeronave en particular proporcionando el ID de esta y los datos que se van a establecer"
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
               @ApiParam(value = "Nevos datos de la aeronave a editar", required = true)
               editada:DtoAeronaveForm,
               @PathVariable
               @ApiParam(value = "Id de la aeronave que se quiere editar", required = true)
               id: UUID): ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(editada, id))
    }


    @ApiOperation(
        value = "Se cambia el estado una aeronave",
        notes = "Se busca una aeronave en particular proporcionando el ID de esta y un número que " +
                "determinará si se cambia su estado(0) o disponibilidad(1)"
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
    @PutMapping("/{id}/{opt}")
    fun cambiarEstado(@PathVariable
                      @ApiParam(value = "Id de la aeronave que se quiere editar", required = true)
                      id: UUID, @PathVariable
                        @ApiParam(value = "Número que determina la propiedad a cambiar", required = true)
                        opt:Int) : ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id, opt))
    }


}