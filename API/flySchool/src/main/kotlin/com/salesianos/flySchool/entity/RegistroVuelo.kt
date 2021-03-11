package com.salesianos.flySchool.entity

import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

@Entity
class RegistroVuelo (

    var fecha : LocalDate,

    var horaInicio : LocalTime,

    var horaFin : LocalTime,

    var tiempo : LocalTime,

    @ManyToOne
    var piloto: Piloto,

    @ManyToOne
    var aeronave: Aeronave,

    var tipoLibre : Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as RegistroVuelo
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}