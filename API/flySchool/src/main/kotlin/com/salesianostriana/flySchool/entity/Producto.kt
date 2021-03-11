package com.salesianostriana.flySchool.entity

import java.time.LocalTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Producto (

    var nombre : String,

    var precio : Double,

    var horasVuelo : LocalTime,

    var tipoLibre : Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}