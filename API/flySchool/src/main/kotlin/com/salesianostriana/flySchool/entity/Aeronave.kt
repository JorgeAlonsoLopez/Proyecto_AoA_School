package com.salesianostriana.flySchool.entity

import java.util.*
import javax.persistence.*

@Entity
class Aeronave (

    var matricula : String,

    var marca : String,

    var modelo : String,

    var motor : String,

    var potencia : Double,

    var autonomia : Double,

    var velMax : Double,

    var velMin : Double,

    var velCru : Double,

    var mantenimiento : Boolean,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "foto_id", referencedColumnName = "id")
    var foto : FotoAeronave,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}