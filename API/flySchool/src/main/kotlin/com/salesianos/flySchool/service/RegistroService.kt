package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoRegistro
import com.salesianos.flySchool.dto.DtoRegistroForm
import com.salesianos.flySchool.dto.toGetDtoRegistro
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.error.AeronaveSearchNotFoundException
import com.salesianos.flySchool.error.ListaRegistroVueloNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.repository.ProductoRepository
import com.salesianos.flySchool.repository.RegistroRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class RegistroService (): BaseService<RegistroVuelo, UUID, RegistroRepository>(){

    override fun save(registro: RegistroVuelo) = super.save(registro)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    fun countByAeronave(aeronave: Aeronave) = repository.countByAeronave(aeronave)

    fun findByPiloto(piloto: Piloto) = repository.findByPiloto(piloto)

    fun difference(start: LocalTime, stop: LocalTime): LocalTime {
        lateinit var end : LocalTime
        var min = stop.minute - start.minute
        var hor = 0
        if(min < 0){
            hor = stop.hour - start.hour - 1
            min = (60+min)
        }else{
            hor = stop.hour - start.hour
        }
        end = LocalTime.of(hor, min)
        return end
    }

    fun listado() : ResponseEntity<List<DtoRegistro>> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findAll().map{it.toGetDtoRegistro()}
            .takeIf { it.isNotEmpty() } ?: throw ListaRegistroVueloNotFoundException(RegistroVuelo::class.java))
    }

    fun listadoUsuario(user: Usuario) : ResponseEntity<List<DtoRegistro>> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findByPiloto(user as Piloto).map{it.toGetDtoRegistro()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaRegistroVueloNotFoundException(RegistroVuelo::class.java))
    }

    fun crear(nueva: DtoRegistroForm, id: UUID, user: Usuario, aeronaveService: AeronaveService, pilotoService: PilotoService) : ResponseEntity<DtoRegistro> {
        val piloto = user as Piloto
        val aeronave = aeronaveService.findById(id).orElseThrow{ AeronaveSearchNotFoundException(id.toString()) }
        val inicio = LocalTime.of(nueva.horaInicio.split(":")[0].toInt(),nueva.horaInicio.split(":")[1].toInt())
        val fin =LocalTime.of(nueva.horaFin.split(":")[0].toInt(),nueva.horaFin.split(":")[1].toInt())
        if(inicio < fin && aeronave != null){
            var registro = RegistroVuelo(
                LocalDate.now(),inicio, fin,
                this.difference(inicio, fin), piloto, aeronave, piloto.licencia!!)
            this.save(registro)
            var tiemp : Double = registro.tiempo.hour.toDouble() + (registro.tiempo.minute.toDouble()/60)
            piloto.horas = piloto.horas!! - tiemp
            pilotoService.save(piloto)
            return ResponseEntity.status(HttpStatus.CREATED).body(registro.toGetDtoRegistro())
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

}