package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.DtoAeronaveForm
import com.salesianos.flySchool.dto.DtoAeronaveResp
import com.salesianos.flySchool.dto.toGetDtoAeronaveResp
import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.FotoAeronave
import com.salesianos.flySchool.service.AeronaveService
import com.salesianos.flySchool.service.FotoAeronaveServicio
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
    private val aeronaveService: AeronaveService,
    private val servicioFoto: FotoAeronaveServicio,
    val imgurStorageService: ImgurStorageService
) {

    @PostMapping("/")
    fun create(@Valid @RequestBody nueva:DtoAeronaveForm, @RequestPart("file") file: MultipartFile) : ResponseEntity<DtoAeronaveResp> {

        try {
            var foto = FotoAeronave()
            var guardado = servicioFoto.save( foto, file)
            var aeronave = Aeronave(nueva.matricula, nueva.marca, nueva.modelo, nueva.motor, nueva.potencia,
                nueva.autonomia, nueva.velMax, nueva.velMin, nueva.velCru, false)
            aeronave.addFoto(guardado)
            aeronaveService.save(aeronave)
            servicioFoto.save(guardado)
            return ResponseEntity.status(HttpStatus.CREATED).body(aeronave.toGetDtoAeronaveResp())
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }

    }

    @DeleteMapping("/{id}/{hash}")
    fun delete(@PathVariable hash: String, @PathVariable id: UUID): ResponseEntity<Any> {

        var aeronave = aeronaveService.findById(id)

        if (aeronave!=null){
            var foto = aeronave.get().foto!!
            if (aeronave.get().foto!!.deleteHash==hash){
                imgurStorageService.delete(hash)
                aeronave.get().deleteFoto()
                servicioFoto.delete(foto)
                aeronaveService.delete(aeronave.get())
            }

        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()

    }

}