package com.salesianos.flySchool.entity

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