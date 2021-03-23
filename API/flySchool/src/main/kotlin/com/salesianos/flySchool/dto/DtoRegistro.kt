package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.RegistroVuelo
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * Clase que guarda la información proveniente del formulario
 */
data class DtoRegistroForm(

    @ApiModelProperty(value = "Hora y minutos en el que la aeronave despegó")
    var horaInicio : String,

    @ApiModelProperty(value = "Hora y minutos en el que la aeronave aterrizó")
    var horaFin : String,
)

/**
 * Clase que guarda la información del producto proveniente de la base de datos
 */
data class DtoRegistro(

        @ApiModelProperty(value = "ID que identifica la entidad")
        val id: UUID,

        @ApiModelProperty(value = "Día en el que se realizó el vuelo")
        var fecha : LocalDate,

        @ApiModelProperty(value = "Hora y minutos en el que la aeronave despegó")
        var horaInicio : LocalTime,

        @ApiModelProperty(value = "Hora y minutos en el que la aeronave aterrizó")
        var horaFin : LocalTime,

        @ApiModelProperty(value = "Tipo de vuelo correspondiente a la fase del piloto (enseñanza o libre)")
        var tipoLibre : Boolean,

        @ApiModelProperty(value = "Piloto que realizó el vuelo")
        var piloto: DtoPilot,

        @ApiModelProperty(value = "Aeronave que se ha utilizado")
        var aeronave: DtoAeronavePeq
)

fun RegistroVuelo.toGetDtoRegistro():DtoRegistro{
    return DtoRegistro( id!!, fecha ,horaInicio, horaFin, tipoLibre, piloto.toGetDtoUserInfoSpeciPilot(), aeronave.toGetDtoAeronavePeq())

}