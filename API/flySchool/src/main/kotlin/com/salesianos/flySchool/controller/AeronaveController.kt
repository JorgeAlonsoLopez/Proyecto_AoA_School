package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.service.AeronaveService
import com.salesianos.flySchool.service.FotoAeronaveServicio
import com.salesianos.flySchool.service.RegistroService
import com.salesianos.flySchool.upload.ImgurStorageService
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

    @PostMapping("/")
    fun crear(@Valid @RequestBody nueva:DtoAeronaveForm): ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(nueva))
    }

    @PostMapping("/{id}")
    fun addFoto(@PathVariable id: UUID, @RequestPart("file") file: MultipartFile) : ResponseEntity<DtoAeronaveResp> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addFoto(id, file, servicioFoto))
    }

    @DeleteMapping("/{id}/{hash}")
    fun delete(@PathVariable hash: String, @PathVariable id: UUID): ResponseEntity<Any> {
        var rest = service.delete(hash, id, imgurStorageService,registroService, servicioFoto)
        if(rest){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

    @DeleteMapping("/foto/{id}/{hash}")
    fun deleteFoto(@PathVariable hash: String, @PathVariable id: UUID) {
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteFoto(hash, id, imgurStorageService,registroService, servicioFoto))
    }

    @GetMapping("/")
    fun listado(): ResponseEntity<List<DtoAeronaveResp>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }

    @GetMapping("/alta")
    fun listadoAlta(): ResponseEntity<List<DtoAeronaveResp>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoAlta())
    }

    @GetMapping("/{id}")
    fun aeronaveId(@PathVariable id: UUID): ResponseEntity<DtoAeronaveResp> {
        return ResponseEntity.status(HttpStatus.OK).body(service.aeronaveId(id))
    }

    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody editada:DtoAeronaveForm, @PathVariable id: UUID): ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(editada, id))
    }

    @PutMapping("/{id}/{opt}")
    fun cambiarEstado(@PathVariable id: UUID, @PathVariable opt:Int) : ResponseEntity<DtoAeronaveSinFoto> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id, opt))
    }


}