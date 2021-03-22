package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Aeronave
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Data class que sirve para guardar la información de una aeronaque que se va a guardar o de una ya existente que se va a editar.
 */
data class DtoAeronaveForm(
    /**
     * Atributo que almacena la matrícula de la aeronave
     */
    @ApiModelProperty(value = "Matrícula de la aeronave", dataType = "java.lang.String")
    @get:NotBlank(message="{aeronave.matricula.blank}")
    var matricula : String,

    /**
     * Atributo que almacena la marca de la aeronave
     */
    @ApiModelProperty(value = "Marca delmodelo de la aeronave", dataType = "java.lang.String")
    @get:NotBlank(message="{aeronave.marca.blank}")
    var marca : String,

    /**
     * Atributo que almacena el modelode la aeronave
     */
    @ApiModelProperty(value = "Modelo de aeronave", dataType = "java.lang.String")
    @get:NotBlank(message="{aeronave.modelo.blank}")
    var modelo : String,

    /**
     * Atributo que almacena el nombre del motor de la aeronave
     */
    @ApiModelProperty(value = "Nombre del motor de la aeronave", dataType = "java.lang.String")
    @get:NotBlank(message="{aeronave.motor.blank}")
    var motor : String,

    /**
     * Atributo que almacena la portencia del motor
     */
    @ApiModelProperty(value = "Potencia del motor de la aeronave", dataType = "Double")
    @get:Min(value=60,message="{aeronave.potencia.min}")
    var potencia : Double,

    /**
     * Atributo que almacena la autonomía de la aeronave
     */
    @ApiModelProperty(value = "Autonomía (km) de la aeronave", dataType = "Double")
    @get:Min(value=50,message="{aeronave.autonomia.min}")
    var autonomia : Double,

    /**
     * Atributo que almacena la velocidad máxima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave", dataType = "Double")
    @get:Min(value=55,message="{aeronave.velMax.min}")
    var velMax : Double,

    /**
     * Atributo que almacena la velocidad mínima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave", dataType = "Double")
    @get:Min(value=20,message="{aeronave.velMin.min}")
    var velMin : Double,

    /**
     * Atributo que almacena la velocidad de crucero de la aeronave
     */
    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave", dataType = "Double")
    @get:Min(value=40,message="{aeronave.velCru.min}")
    var velCru : Double
)

data class DtoAeronaveResp(
    /**
     * Atributo que almacena el ID de la aeronave
     */
    @ApiModelProperty(value = "El ID de la aeronave", dataType = "java.util.UUID")
    val id:UUID,

    /**
     * Atributo que almacena la matrícula de la aeronave
     */
    @ApiModelProperty(value = "Matrícula de la aeronave", dataType = "java.lang.String")
    var matricula : String,

    /**
     * Atributo que almacena la marca de la aeronave
     */
    @ApiModelProperty(value = "Marca del modelo de la aeronave", dataType = "java.lang.String")
    var marca : String,

    /**
     * Atributo que almacena el modelode la aeronave
     */
    @ApiModelProperty(value = "Modelo de aeronave", dataType = "java.lang.String")
    var modelo : String,

    /**
     * Atributo que almacena el nombre del motor de la aeronave
     */
    @ApiModelProperty(value = "Nombre del motor de la aeronave", dataType = "java.lang.String")
    var motor : String,

    /**
     * Atributo que almacena la portencia del motor
     */
    @ApiModelProperty(value = "Potencia del motor de la aeronave", dataType = "Double")
    var potencia : Double,

    /**
     * Atributo que almacena la autonomía de la aeronave
     */
    @ApiModelProperty(value = "Autonomía (km) de la aeronave", dataType = "Double")
    var autonomia : Double,

    /**
     * Atributo que almacena la velocidad máxima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave", dataType = "Double")
    var velMax : Double,

    /**
     * Atributo que almacena la velocidad mínima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave", dataType = "Double")
    var velMin : Double,

    /**
     * Atributo que almacena la velocidad de crucero de la aeronave
     */
    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave", dataType = "Double")
    var velCru : Double,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var mantenimiento : Boolean,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var alta: Boolean,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
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

data class DtoAeronaveSinFoto(
    /**
     * Atributo que almacena el ID de la aeronave
     */
    @ApiModelProperty(value = "El ID de la aeronave", dataType = "java.util.UUID")
    val id:UUID,

    /**
     * Atributo que almacena la matrícula de la aeronave
     */
    @ApiModelProperty(value = "Matrícula de la aeronave", dataType = "java.lang.String")
    var matricula : String,

    /**
     * Atributo que almacena la marca de la aeronave
     */
    @ApiModelProperty(value = "Marca del modelo de la aeronave", dataType = "java.lang.String")
    var marca : String,

    /**
     * Atributo que almacena el modelode la aeronave
     */
    @ApiModelProperty(value = "Modelo de aeronave", dataType = "java.lang.String")
    var modelo : String,

    /**
     * Atributo que almacena el nombre del motor de la aeronave
     */
    @ApiModelProperty(value = "Nombre del motor de la aeronave", dataType = "java.lang.String")
    var motor : String,

    /**
     * Atributo que almacena la portencia del motor
     */
    @ApiModelProperty(value = "Potencia del motor de la aeronave", dataType = "Double")
    var potencia : Double,

    /**
     * Atributo que almacena la autonomía de la aeronave
     */
    @ApiModelProperty(value = "Autonomía (km) de la aeronave", dataType = "Double")
    var autonomia : Double,

    /**
     * Atributo que almacena la velocidad máxima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad máxima (km/h) de la aeronave", dataType = "Double")
    var velMax : Double,

    /**
     * Atributo que almacena la velocidad mínima de la aeronave
     */
    @ApiModelProperty(value = "Velocidad mínima (km/h) de la aeronave", dataType = "Double")
    var velMin : Double,

    /**
     * Atributo que almacena la velocidad de crucero de la aeronave
     */
    @ApiModelProperty(value = "Velocidad de crucero (km/h) de la aeronave", dataType = "Double")
    var velCru : Double,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var mantenimiento : Boolean,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var alta: Boolean
)

fun Aeronave.toGetDtoAeronaveSinFoto():DtoAeronaveSinFoto{
    return DtoAeronaveSinFoto(id!!, matricula, marca , modelo, motor, potencia, autonomia, velMax, velMin, velCru, mantenimiento, alta)

}

data class DtoAeronavePeq(
    /**
     * Atributo que almacena el ID de la aeronave
     */
    @ApiModelProperty(value = "El ID de la aeronave", dataType = "java.util.UUID")
    val id:UUID,

    /**
     * Atributo que almacena la matrícula de la aeronave
     */
    @ApiModelProperty(value = "Matrícula de la aeronave", dataType = "java.lang.String")
    var matricula : String,

    /**
     * Atributo que almacena la marca de la aeronave
     */
    @ApiModelProperty(value = "Marca del modelo de la aeronave", dataType = "java.lang.String")
    var marca : String,

    /**
     * Atributo que almacena el modelode la aeronave
     */
    @ApiModelProperty(value = "Modelo de aeronave", dataType = "java.lang.String")
    var modelo : String,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var mantenimiento : Boolean,

    /**
     * Atributo que almacena
     */
    @ApiModelProperty(value = "", dataType = "java.lang.String")
    var alta: Boolean
)

fun Aeronave.toGetDtoAeronavePeq():DtoAeronavePeq{
    return DtoAeronavePeq(id!!, matricula, marca , modelo, mantenimiento, alta)

}

