package com.salesianos.flySchool.entity

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Aeronave (
    @Column(nullable = false, unique = true)
    var matricula : String,

    var marca : String,

    var modelo : String,

    var motor : String,

    var potencia : Double,

    var autonomia : Double,

    var velMax : Double,

    var velMin : Double,

    var velCru : Double,

    var mantenimiento : Boolean = false,

    var alta : Boolean = true,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "foto_id", referencedColumnName = "id")
    var foto : FotoAeronave? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Aeronave
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }

    fun addFoto(foto:FotoAeronave){
        this.foto = foto
        foto.aeronave = this
    }

    fun deleteFoto(){
        this.foto!!.aeronave = null
        this.foto = null
    }

}