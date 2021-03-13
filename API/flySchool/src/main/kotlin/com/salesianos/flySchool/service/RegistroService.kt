package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.RegistroVuelo
import com.salesianos.flySchool.repository.ProductoRepository
import com.salesianos.flySchool.repository.RegistroRepository
import java.util.*

class RegistroService (): BaseService<RegistroVuelo, UUID, RegistroRepository>(){

    override fun save(registro: RegistroVuelo) = super.save(registro)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    fun countByAeronave(aeronave: Aeronave) = repository.countByAeronave(aeronave)

}