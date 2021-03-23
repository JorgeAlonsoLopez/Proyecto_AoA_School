package com.salesianos.flySchool.entity

import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

/**
 * Entidad que hace referencia a los registros de los vuelos realizados por los pilotos
 */
@Entity
class RegistroVuelo (

    /**
     * Propiedad que hace referencia a la fecha en la que se realizó el vuelo
     */
    var fecha : LocalDate,

    /**
     * Propiedad que hace referencia a la hora y minutos en el que el piloto despegó
     */
    var horaInicio : LocalTime,

    /**
     * Propiedad que hace referencia a la hora y minutos en el que el piloto aterrizó
     */
    var horaFin : LocalTime,

    /**
     * Propiedad que hace referencia a el tiempo (horas y minutos) trascurrido entre el despegue y el aterrizaje
     */
    var tiempo : LocalTime,

    /**
     * Propiedad que hace referencia al piloto que ha realizado el vuelo
     */
    @ManyToOne
    var piloto: Piloto,

    /**
     * Propiedad que hace referencia a la aeronave usada para el vuelo
     */
    @ManyToOne
    var aeronave: Aeronave,

    /**
     * Propiedad que hace referencia al tipo de vuelo llevado a cabo por el piloto. El valor es el mismo
     * que el de la propiedad licencia de la entidad Piloto, ya que están relacionadas. El vuelo es de instrucción
     * (esta propiedad toma el valor de false) si no ha obtenido la licencia y en el caso de obtenerla y llevar a cabo un vuelo,
     * este será de tipo libre (esta propiedad toma el valor de true)
     */
    var tipoLibre : Boolean,

    /**
     * Propiedad que hace referencia al ID de la entidad
     */
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