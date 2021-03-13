package com.salesianos.flySchool.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Factura (

    @get:Min(value=1,message="{factura.precio.min}")
    var precioTotal : Double,

    var fecha : LocalDateTime,

    @ManyToOne
    var comprador: Piloto,

    @ManyToOne
    var producto: Producto,

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