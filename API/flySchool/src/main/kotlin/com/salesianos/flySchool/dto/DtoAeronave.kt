package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Aeronave
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Data class que sirve para tratar la información de una aeronave.
 */
data class DtoAeronaveForm(

    @ApiModelProperty(value = "Matrícula de la aeronave")
    @get:NotBlank(message="{aeronave.matricula.blank}")
    var matricula : String,

    @ApiModelProperty(value = "Marca del modelo de la aeronave")
    @get:NotBlank(message="{aeronave.marca.blank}")
    var marca : String,

    @ApiModelProperty(value = "Modelo de aeronave")
    @get:NotBlank(message="{aeronave.modelo.blank}")
    var modelo : String,

    @ApiModelProperty(value = "Nombre del motor de la aeronave")
    @get:NotBlank(message="{aeronave.motor.blank}")
    var motor : String,

    @ApiModelProperty(value = "Potencia del motor de la aeronave")
    @get:Min(value=60,message="{aeronave.potencia.min}")
    var potencia : Double,

    @ApiModelProperty(value = "Autonomía (km) de la aeronave")
    @get:Min(value=50,message="{aeronave.autonomia.min}")
    var autonomia : Double,

    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave")
    @get:Min(value=55,message="{aeronave.velMax.min}")
    var velMax : Double,

    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave")
    @get:Min(value=20,message="{aeronave.velMin.min}")
    var velMin : Double,

    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave")
    @get:Min(value=40,message="{aeronave.velCru.min}")
    var velCru : Double
)

/**
 * Data class que sirve para tratar la información de la respuesta del servidor  para una aeronave.
 */
data class DtoAeronaveResp(

    @ApiModelProperty(value = "ID de la eronave")
    val id:UUID,

    @ApiModelProperty(value = "Matrícula de la aeronave")
    var matricula : String,

    @ApiModelProperty(value = "Marca del modelo de la aeronave")
    var marca : String,

    @ApiModelProperty(value = "Modelo de aeronave")
    var modelo : String,

    @ApiModelProperty(value = "Nombre del motor de la aeronave")
    var motor : String,

    @ApiModelProperty(value = "Potencia del motor de la aeronave")
    var potencia : Double,

    @ApiModelProperty(value = "Autonomía (km) de la aeronave")
    var autonomia : Double,

    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave")
    var velMax : Double,

    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave")
    var velMin : Double,

    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave")
    var velCru : Double,

    @ApiModelProperty(value = "Estado de la aeronave referente al mantenimiento")
    var mantenimiento : Boolean,

    @ApiModelProperty(value = "Estado de la aeronave referente a la disponibilidad")
    var alta: Boolean,

    var fotoURL:DTOFotoUrl
)

fun Aeronave.toGetDtoAeronaveResp():DtoAeronaveResp{
    if(foto == null){
        var dto = DTOFotoUrl("https://i.imgur.com/xVwZqCr.png", "n")
        return DtoAeronaveResp(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento!!, alta, dto )
    }else{
        return DtoAeronaveResp(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento!!, alta, foto!!.toGetDTOFotoUrl() )
    }

}

/**
 * Data class que sirve para tratar la información de la respuesta del servidor  para una aeronave, sin necesitar la foto.
 */
data class DtoAeronaveSinFoto(

    @ApiModelProperty(value = "ID de la eronave")
    val id:UUID,

    @ApiModelProperty(value = "Matrícula de la aeronave")
    var matricula : String,

    @ApiModelProperty(value = "Marca del modelo de la aeronave")
    var marca : String,

    @ApiModelProperty(value = "Modelo de aeronave")
    var modelo : String,

    @ApiModelProperty(value = "Nombre del motor de la aeronave")
    var motor : String,

    @ApiModelProperty(value = "Potencia del motor de la aeronave")
    var potencia : Double,

    @ApiModelProperty(value = "Autonomía (km) de la aeronave")
    var autonomia : Double,

    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave")
    var velMax : Double,

    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave")
    var velMin : Double,

    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave")
    var velCru : Double,

    @ApiModelProperty(value = "Estado de la aeronave referente al mantenimiento")
    var mantenimiento : Boolean,

    @ApiModelProperty(value = "Estado de la aeronave referente a la disponibilidad")
    var alta: Boolean
)

fun Aeronave.toGetDtoAeronaveSinFoto():DtoAeronaveSinFoto{
    return DtoAeronaveSinFoto(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento, alta)

}

/**
 * Data class que sirve para tratar la información de la respuesta del servidor  para una aeronave.
 */
data class DtoAeronavePeq(

    @ApiModelProperty(value = "ID de la eronave")
    val id:UUID,

    @ApiModelProperty(value = "Matrícula de la aeronave")
    var matricula : String,

    @ApiModelProperty(value = "Marca del modelo de la aeronave")
    var marca : String,

    @ApiModelProperty(value = "Modelo de aeronave")
    var modelo : String,

    @ApiModelProperty(value = "Estado de la aeronave referente al mantenimiento")
    var mantenimiento : Boolean,

    @ApiModelProperty(value = "Estado de la aeronave referente a la disponibilidad")
    var alta: Boolean
)

fun Aeronave.toGetDtoAeronavePeq():DtoAeronavePeq{
    return DtoAeronavePeq(id!!, matricula, marca , modelo, mantenimiento, alta)

}

