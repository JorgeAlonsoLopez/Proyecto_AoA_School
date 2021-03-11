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
    @Column(nullable = false, unique = true)
    var tarjetaCredito :  String,

    var horas : Double? = 0.0,

    var licencia : Boolean? = false,

    var alta : Boolean? = true)

    : Usuario(usuario, password, email, telefono, nombreCompleto,
    fechaNacimiento, roles) {
}