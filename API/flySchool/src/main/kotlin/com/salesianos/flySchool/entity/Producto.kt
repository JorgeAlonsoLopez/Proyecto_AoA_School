package com.salesianos.flySchool.entity

import java.time.LocalTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Producto (

    @get:NotBlank(message="{producto.nombre.blank}")
    var nombre : String,

    @get:NotBlank(message="{producto.precio.blank}")
    @get:Min(value=1,message="{producto.precio.min}")
    var precio : Double,

    @get:NotBlank(message="{producto.horasVuelo.blank}")
    @get:Min(value=0,message="{producto.horasVuelo.min}")
    var horasVuelo : Int,

    var tipoLibre : Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Producto
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}