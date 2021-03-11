package com.salesianostriana.flySchool.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Factura (

    @get:Min(value=1,message="{factura.precio.min}")
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