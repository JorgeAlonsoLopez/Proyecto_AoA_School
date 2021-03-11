package com.salesianostriana.flySchool.entity

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Aeronave (
    @get:NotBlank(message="{aeronave.matricula.blank}")
    @Column(nullable = false, unique = true)
    var matricula : String,

    @get:NotBlank(message="{aeronave.marca.blank}")
    var marca : String,

    @get:NotBlank(message="{aeronave.modelo.blank}")
    var modelo : String,

    @get:NotBlank(message="{aeronave.motor.blank}")
    var motor : String,

    @get:NotBlank(message="{aeronave.potencia.blank}")
    @get:Min(value=60,message="{aeronave.potencia.min}")
    var potencia : Double,

    @get:NotBlank(message="{aeronave.autonomia.blank}")
    @get:Min(value=50,message="{aeronave.autonomia.min}")
    var autonomia : Double,

    @get:NotBlank(message="{aeronave.velMax.blank}")
    @get:Min(value=55,message="{aeronave.velMax.min}")
    var velMax : Double,

    @get:NotBlank(message="{aeronave.velMin.blank}")
    @get:Min(value=20,message="{aeronave.velMin.min}")
    var velMin : Double,

    @get:NotBlank(message="{aeronave.velCru.blank}")
    @get:Min(value=40,message="{aeronave.velCru.min}")
    var velCru : Double,

    var mantenimiento : Boolean? = false,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "foto_id", referencedColumnName = "id")
    var foto : FotoAeronave? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
){
}