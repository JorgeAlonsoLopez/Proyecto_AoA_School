package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Aeronave
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class DtoAeronaveForm(
    @get:NotBlank(message="{aeronave.matricula.blank}")
    var matricula : String,

    @get:NotBlank(message="{aeronave.marca.blank}")
    var marca : String,

    @get:NotBlank(message="{aeronave.modelo.blank}")
    var modelo : String,

    @get:NotBlank(message="{aeronave.motor.blank}")
    var motor : String,

    @get:Min(value=60,message="{aeronave.potencia.min}")
    var potencia : Double,

    @get:Min(value=50,message="{aeronave.autonomia.min}")
    var autonomia : Double,

    @get:Min(value=55,message="{aeronave.velMax.min}")
    var velMax : Double,

    @get:Min(value=20,message="{aeronave.velMin.min}")
    var velMin : Double,

    @get:Min(value=40,message="{aeronave.velCru.min}")
    var velCru : Double
)

data class DtoAeronaveResp(
    val id:UUID,
    var matricula : String,
    var marca : String,
    var modelo : String,
    var motor : String,
    var potencia : Double,
    var autonomia : Double,
    var velMax : Double,
    var velMin : Double,
    var velCru : Double,
    var mantenimiento : Boolean,
    var alta: Boolean,
    var fotoURL:DTOFotoUrl
)

fun Aeronave.toGetDtoAeronaveResp():DtoAeronaveResp{
    return DtoAeronaveResp(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento!!, alta, foto!!.toGetDTOFotoUrl() )
}

data class DtoAeronaveSinFoto(
    val id:UUID,
    var matricula : String,
    var marca : String,
    var modelo : String,
    var motor : String,
    var potencia : Double,
    var autonomia : Double,
    var velMax : Double,
    var velMin : Double,
    var velCru : Double,
    var mantenimiento : Boolean,
    var alta: Boolean
)

fun Aeronave.toGetDtoAeronaveSinFoto():DtoAeronaveSinFoto{
    return DtoAeronaveSinFoto(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento!!, alta)

}

