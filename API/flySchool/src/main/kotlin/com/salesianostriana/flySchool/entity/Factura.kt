package com.salesianostriana.flySchool.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Factura (

    var precioTotal : Double,

    var fecha : LocalDateTime,

    @ManyToOne
    var comprador: Piloto,

    @ManyToOne
    var producto: Producto,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}