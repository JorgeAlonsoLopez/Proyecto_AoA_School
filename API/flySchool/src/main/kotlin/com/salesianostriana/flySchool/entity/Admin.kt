package com.salesianostriana.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Admin (usuario:String, password:String, email:String, telefono:String,
             nombreCompleto:String, fechaNacimiento: LocalDate)
    : Usuario(usuario, password, email, telefono, nombreCompleto,
    fechaNacimiento){
}