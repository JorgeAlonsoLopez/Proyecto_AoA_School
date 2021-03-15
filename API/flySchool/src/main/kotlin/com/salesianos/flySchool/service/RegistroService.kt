package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.RegistroVuelo
import com.salesianos.flySchool.repository.ProductoRepository
import com.salesianos.flySchool.repository.RegistroRepository
import org.springframework.stereotype.Service
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
        stop.minusMinutes(start.minute.toLong())
        stop.minusHours(start.hour.toLong())
        return stop
    }

}