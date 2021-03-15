package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.FotoAeronave
import com.salesianos.flySchool.error.*
import com.salesianos.flySchool.service.AeronaveService
import com.salesianos.flySchool.service.FotoAeronaveServicio
import com.salesianos.flySchool.service.RegistroService
import com.salesianos.flySchool.upload.ImgurBadRequest
import com.salesianos.flySchool.upload.ImgurStorageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/aeronave")
class ImagenController(
    private val service: AeronaveService,
    private val servicioFoto: FotoAeronaveServicio,
    private val imgurStorageService: ImgurStorageService,
    private val registroService:RegistroService
) {

    @PostMapping("/")
    fun crear(@Valid @RequestBody nueva:DtoAeronaveForm) : ResponseEntity<DtoAeronaveSinFoto> {
        var aeronave = Aeronave(nueva.matricula, nueva.marca, nueva.modelo, nueva.motor, nueva.potencia,
            nueva.autonomia, nueva.velMax, nueva.velMin, nueva.velCru, false)
        service.save(aeronave)
        return ResponseEntity.status(HttpStatus.CREATED).body(aeronave.toGetDtoAeronaveSinFoto())
    }

    @PostMapping("/{id}")
    fun addFoto(@PathVariable id: UUID, @RequestPart("file") file: MultipartFile) : ResponseEntity<DtoAeronaveResp> {

        try {
            var foto = FotoAeronave()
            var guardado = servicioFoto.save( foto, file)
            var aeronave = service.findById(id).get()
            aeronave.addFoto(guardado)
            service.save(aeronave)
            servicioFoto.save(guardado)
            return ResponseEntity.status(HttpStatus.CREATED).body(aeronave.toGetDtoAeronaveResp())
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }
    }

    @DeleteMapping("/{id}/{hash}")
    fun delete(@PathVariable hash: String, @PathVariable id: UUID): ResponseEntity<Any> {

        var aeronave = service.findById(id)
        if(aeronave!=null){
            if (registroService.countByAeronave(aeronave.get()) == 0 ){
                var foto = aeronave.get().foto!!
                if (aeronave.get().foto!!.deleteHash==hash){
                    imgurStorageService.delete(hash)
                    aeronave.get().deleteFoto()
                    servicioFoto.delete(foto)
                    service.delete(aeronave.get())
                }
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }

    }

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoAeronaveSinFoto>> {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll().map{it.toGetDtoAeronaveSinFoto()}
                .takeIf { it.isNotEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java))
    }

    @GetMapping("/alta")
    fun listadoAlta() : ResponseEntity<List<DtoAeronaveSinFoto>> {// it.isNotEmpty()
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllAlta()?.map{it.toGetDtoAeronaveSinFoto()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java))
    }

    @GetMapping("/{id}")
    fun aeronaveId(@PathVariable id: UUID): ResponseEntity<DtoAeronaveResp> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).map { it.toGetDtoAeronaveResp() }
            .orElseThrow { AeronaveSearchNotFoundException(id.toString()) })
    }

    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody editada:DtoAeronaveForm, @PathVariable id: UUID) : ResponseEntity<DtoAeronaveSinFoto> {

        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id)
            .map { fromRepo ->
                fromRepo.matricula = editada.matricula
                fromRepo.marca = editada.marca
                fromRepo.modelo = editada.modelo
                fromRepo.motor = editada.motor
                fromRepo.potencia = editada.potencia
                fromRepo.autonomia = editada.autonomia
                fromRepo.velMax = editada.velMax
                fromRepo.velMin = editada.velMin
                fromRepo.velCru = editada.velCru
                service.save(fromRepo).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) })
    }

    @PutMapping("/{id}/{opt}")
    fun cambiarEstado(@PathVariable id: UUID, @PathVariable opt:Int) : ResponseEntity<DtoAeronaveSinFoto> {

        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id)
            .map { aero ->
                if(opt==1)
                    aero.mantenimiento = !aero.mantenimiento
                else
                    aero.alta = !aero.alta
                service.save(aero).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) })
    }





}