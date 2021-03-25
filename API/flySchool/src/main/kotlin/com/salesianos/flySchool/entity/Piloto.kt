package com.salesianos.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Entidad que hace referencia al usuario de tipo piloto. Hereda de la entidad Usuario
 * @see Usuario
 */
@Entity
class Piloto (

    usuario:String,
    password:String,
    email:String,
    telefono:String,
    nombreCompleto:String,
    fechaNacimiento: LocalDate,
    roles: MutableSet<String> = HashSet(),

    /**
     * Propiedad que hace referencia a la tarjeta de crédito del piloto
     */
    @get:NotBlank(message="{usuario.tarjeta.blank}")
    var tarjetaCredito :  String,

    /**
     * Propiedad que hace referencia al número de horas que dispone el piloto para volar. Puede adquirir más
     * comprando paquetes de horas (entidad Producto) y se gasta al registrar los vuelos realizados (entidad RegistroVuelo)
     * @see Producto
     * @see RegistroVuelo
     */
    var horas : Double = 0.0,

    /**
     * Propiedad que hace referencia a la posesión o no de la licencia de vuelo por parte del piloto
     */
    var licencia : Boolean = false,

    /**
     * Propiedad que hace referencia al estado de la cuenta del piloto
     */
    var alta : Boolean = true,

    )
    : Usuario(usuario, password, email, telefono, nombreCompleto,
    fechaNacimiento, roles) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Piloto
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}