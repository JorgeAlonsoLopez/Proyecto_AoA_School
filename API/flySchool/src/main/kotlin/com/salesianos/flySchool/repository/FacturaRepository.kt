package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Factura
 * @see Factura
 */
@Repository
interface FacturaRepository : JpaRepository<Factura, UUID> {

    /**
     * Query que busca el número de facturas pertenecientes a un determinado producto
     */
    fun countByProducto(producto: Producto): Int

    /**
     * Query que busca las facturas pertenecientes a un determinado piloto
     */
    fun findByComprador(comprador : Piloto): List<Factura>

}