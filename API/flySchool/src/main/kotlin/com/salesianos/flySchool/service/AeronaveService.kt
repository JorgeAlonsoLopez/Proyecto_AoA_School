package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.FotoAeronave
import com.salesianos.flySchool.error.AeronaveModifNotFoundException
import com.salesianos.flySchool.error.AeronaveSearchNotFoundException
import com.salesianos.flySchool.error.ListaAeronaveNotFoundException
import com.salesianos.flySchool.repository.AeronaveRepository
import com.salesianos.flySchool.upload.ImgurBadRequest
import com.salesianos.flySchool.upload.ImgurStorageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class AeronaveService(
): BaseService<Aeronave, UUID, AeronaveRepository>()
{

    override fun save(aeronave: Aeronave) = super.save(aeronave)

    override fun findAll() = super.findAll()

    fun findAllAlta() = repository.findByAlta(true)

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    fun create (nueva: DtoAeronaveForm): DtoAeronaveSinFoto {
        var aeronave = Aeronave(nueva.matricula, nueva.marca, nueva.modelo, nueva.motor, nueva.potencia,
            nueva.autonomia, nueva.velMax, nueva.velMin, nueva.velCru, false)
        this.save(aeronave)
        return aeronave.toGetDtoAeronaveSinFoto()
    }

    fun addFoto(id: UUID, file: MultipartFile, servicioFoto:FotoAeronaveServicio): DtoAeronaveResp {
        try {
            var foto = FotoAeronave()
            var guardado = servicioFoto.save( foto, file)
            var aeronave = this.findById(id).get()
            aeronave.addFoto(guardado)
            this.save(aeronave)
            servicioFoto.save(guardado)
            return aeronave.toGetDtoAeronaveResp()
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }
    }

    fun delete(hash: String, id: UUID, imgurStorageService: ImgurStorageService,
        registroService: RegistroService, servicioFoto: FotoAeronaveServicio): Boolean {
        var aeronave = this.findById(id).orElseThrow{ListaAeronaveNotFoundException(Aeronave::class.java)}
        if (registroService.countByAeronave(aeronave) == 0 ){
            if(aeronave.foto == null){
                this.delete(aeronave)
            }else{
                var foto = aeronave.foto!!
                if (aeronave.foto!!.deleteHash==hash){
                    imgurStorageService.delete(hash)
                    aeronave.deleteFoto()
                    servicioFoto.delete(foto)
                    this.delete(aeronave)
                }
            }
            return true
        }else{
            return false
        }
    }

    fun deleteFoto(hash: String, id: UUID, imgurStorageService: ImgurStorageService,
               registroService: RegistroService, servicioFoto: FotoAeronaveServicio): Unit {
        var aeronave = this.findById(id).orElseThrow{ListaAeronaveNotFoundException(Aeronave::class.java)}
        if(aeronave.foto != null){
            var foto = aeronave.foto!!
            if (aeronave.foto!!.deleteHash==hash){
                imgurStorageService.delete(hash)
                aeronave.deleteFoto()
                servicioFoto.delete(foto)
                this.edit(aeronave)
            }
        }

    }

    fun listado(): List<DtoAeronaveResp> {
        return this.findAll().map{it.toGetDtoAeronaveResp()}
            .takeIf { it.isNotEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java)
    }

    fun listadoAlta(): List<DtoAeronaveResp> {
        return this.findAllAlta()?.map{it.toGetDtoAeronaveResp()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java)
    }

    fun aeronaveId(id: UUID): DtoAeronaveResp? {
        return this.findById(id).map { it.toGetDtoAeronaveResp() }
            .orElseThrow { AeronaveSearchNotFoundException(id.toString()) }
    }

    fun editar(editada:DtoAeronaveForm, id: UUID): DtoAeronaveSinFoto? {
        return this.findById(id)
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
                this.save(fromRepo).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) }
    }

    fun cambiarEstado(id: UUID, opt:Int): DtoAeronaveSinFoto? {
        return this.findById(id)
            .map { aero ->
                if(opt==1)
                    aero.mantenimiento = !aero.mantenimiento
                else
                    aero.alta = !aero.alta
                this.save(aero).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) }
    }


}