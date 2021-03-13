package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.repository.ProductoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductoService(
): BaseService<Producto, UUID, ProductoRepository>()
{

    override fun save(produc: Producto) = super.save(produc)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

}