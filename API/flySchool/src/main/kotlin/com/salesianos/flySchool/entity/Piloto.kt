package com.salesianos.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Piloto (

    usuario:String,
    password:String,
    email:String,
    telefono:String,
    nombreCompleto:String,
    fechaNacimiento: LocalDate,
    roles: MutableSet<String> = HashSet(),

    @get:NotBlank(message="{usuario.tarjeta.blank}")
    var tarjetaCredito :  String,
    var horas : Double? = 0.0,
    var licencia : Boolean? = false,
    var alta : Boolean? = true,


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