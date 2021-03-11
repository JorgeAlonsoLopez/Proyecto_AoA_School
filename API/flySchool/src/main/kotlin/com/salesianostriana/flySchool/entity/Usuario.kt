package com.salesianostriana.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Usuario (

    var usuario : String,

    var password : String,

    var email : String,

    var telefono : String,

    var nombreCompleto : String,

    var fechaNacimiento : LocalDate,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
        ) {
}