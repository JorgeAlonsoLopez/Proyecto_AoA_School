package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.repository.AeronaveRepository
import com.salesianos.flySchool.repository.FacturaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class FacturaService(): BaseService<Factura, UUID, FacturaRepository>() {

    override fun save(fact: Factura) = super.save(fact)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    fun countByProducto(producto: Producto) = repository.countByProducto(producto)

    fun findByComprador(piloto: Piloto) = repository.findByComprador(piloto)

}