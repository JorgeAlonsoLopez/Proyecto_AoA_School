package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FacturaRepository : JpaRepository<Factura, UUID> {

    fun countByProducto(producto: Producto): Int

    fun findByComprador(comprador : Piloto): List<Factura>

}