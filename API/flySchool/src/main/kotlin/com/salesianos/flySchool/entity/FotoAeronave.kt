package com.salesianos.flySchool.entity

import java.util.*
import javax.persistence.*

/**
 * Entidad que hace referencia a la foto, en el caso de que la haya, de una aeronave en cuestión
 */
@Entity
class FotoAeronave (

    /**
     * Atributo que almacena la url de la imagen
     */
    var url : String? = null,

    /**
     * Atributo que guarda el hash para poder eliminar dicha foto sel servicio de almacenamiento externo
     */
    var deleteHash : String? = null,

    /**
     * Aeronave a la que hace referencia dicha foto
     */
    @OneToOne(mappedBy = "foto")
    var aeronave: Aeronave? = null,

    /**
     * ID que identifica al objeto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as FotoAeronave
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}