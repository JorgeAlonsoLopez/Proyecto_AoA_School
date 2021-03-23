package com.salesianos.flySchool.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Entidad que hace referencia a un producto (paquete de horas de vuelo)
 */
@Entity
class Producto (

    /**
     * Propiedad que guarda el nombre del producto
     */
    var nombre : String,

    /**
     * Propiedad que guarda le precio del producto
     */
    var precio : Double,

    /**
     * Propiedad que guerda el número de horas de vuelo aue va a adquirir el piloto que comprará dicho producto
     */
    var horasVuelo : Int,

    /**
     * El tipo del producto, que es similar al tipo de pilotos a los que van dirigidos. Si están en
     * el periodo de formación, esta propiedaa será false. Si se da el caso de que ya han obtenido la licencia, por lo tanto
     * sus vuelos son libres, el valor de la propiedad es true
     */
    var tipoLibre : Boolean,

    /**
     * Indica el estado del producto, reflejando su disponibilidad para ser comprado o no
     */
    var alta : Boolean = true,

    /**
     * ID que identifica al producto en cuestión
     */
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