package com.salesianos.flySchool.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

/**
 * Entidad que hace referencia a las facturas de compra de los productos, llevadas a cabo por los pilotos
 */
@Entity
class Factura (

    /**
     * Atributo que almacena el precio total de la compra
     */
    @get:Min(value=1,message="{factura.precio.min}")
    var precioTotal : Double,

    /**
     * Atributo que almacena la fecha en la que se ha llevado a cabo la operación
     */
    var fecha : LocalDateTime,

    /**
     * Atributo que almacena el comprador queha llevado a cabo la operación
     */
    @ManyToOne
    var comprador: Piloto,

    /**
     * Atributo que almacena el producto comprado
     */
    @ManyToOne
    var producto: Producto,

    /**
     * Atributo que almacena el ID de la factura
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Factura
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}