package com.salesianos.flySchool.entity

import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Entidad que hace referencia a las aeronaves, con sus respectivas propiedades
 */
@Entity
class Aeronave (

    /**
     * Atributo que almacena la matricula de la aeronave
     */
    @Column(nullable = false, unique = true)
    var matricula : String,

    /**
     * Atributo que almacena la marca de la aeronave
     */
    var marca : String,

    /**
     * Atributo que almacena el modelo de la aeronave
     */
    var modelo : String,

    /**
     * Atributo que almacena el nombre del motor de la aeronave
     */
    var motor : String,

    /**
     * Atributo que almacena la portencia del motor, en HP
     */
    var potencia : Double,

    /**
     * Atributo que almacena la autonomía de la aeronave, en Km
     */
    var autonomia : Double,

    /**
     * Atributo que almacena la velocidad máxima de la aeronave, en Km/h
     */
    var velMax : Double,

    /**
     * Atributo que almacena la velocidad mínima de la aeronave en Km/h
     */
    var velMin : Double,

    /**
     * Atributo que almacena la velocidad de crucero de la aeronave en Km/h
     */
    var velCru : Double,

    /**
     * Atributo que almacena el estado, referente al mantenimiento, de la aeronave
     */
    var mantenimiento : Boolean = false,

    /**
     * Atributo que almacena el estado, referente a la disponibilidad, de la aeronave
     */
    var alta : Boolean = true,

    /**
    * Atributo que almacena la foto de la aeronave
    */
    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "foto_id", referencedColumnName = "id")
    var foto : FotoAeronave? = null,

    /**
     * Atributo que almacena el ID de la aeronave
     */
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