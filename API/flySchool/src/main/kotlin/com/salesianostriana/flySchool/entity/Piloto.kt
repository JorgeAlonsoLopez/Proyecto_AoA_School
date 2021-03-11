package com.salesianostriana.flySchool.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Piloto (

    var tarjetaCredito :  String,

    var horas : Double,

    var licencia : Boolean,

    var alta : Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}