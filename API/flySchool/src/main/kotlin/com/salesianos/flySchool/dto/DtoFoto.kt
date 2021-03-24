package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.FotoAeronave
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * Clase que guarda la información de la foto relacionada con una aeronave en particular
 */
data class DTOFoto(

    @ApiModelProperty(value = "URL de la foto")
    var url : String,

    @ApiModelProperty(value = "Hash de eliminación de la foto")
    var deleteHash : String,

    @ApiModelProperty(value = "ID de la foto")
    val id: UUID
)


fun FotoAeronave.toGetDTOFoto():DTOFoto{
    return DTOFoto( dataI!!, deleteHash!! ,id!!)

}
/**
 * Clase que guarda la información de la foto relacionada con una aeronave en particular
 */
data class DTOFotoUrl(

    @ApiModelProperty(value = "URL de la foto")
    var url : String,

    @ApiModelProperty(value = "Hash de eliminación de la foto")
    var hash : String
)


fun FotoAeronave.toGetDTOFotoUrl():DTOFotoUrl{
    return DTOFotoUrl(dataI!!, deleteHash!!)

}