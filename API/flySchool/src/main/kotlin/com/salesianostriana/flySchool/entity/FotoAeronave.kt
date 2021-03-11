package com.salesianostriana.flySchool.entity

import java.util.*
import javax.persistence.*

@Entity
class FotoAeronave (
    var dataId : String? = null,
    var deleteHash : String? = null,

    @OneToOne(mappedBy = "foto")
    var aeronave: Aeronave? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}