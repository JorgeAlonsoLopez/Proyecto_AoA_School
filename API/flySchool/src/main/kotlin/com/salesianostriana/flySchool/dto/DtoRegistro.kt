package com.salesianostriana.flySchool.dto

import com.salesianostriana.flySchool.entity.RegistroVuelo
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class DtoRegistroForm(
    var horaInicio : String,
    var horaFin : String,
)

data class DtoRegistro(
    val id: UUID,
    var fecha : LocalDate,
    var horaInicio : LocalTime,
    var horaFin : LocalTime,
    var tipoLibre : Boolean
)

fun RegistroVuelo.toGetDtoRegistro():DtoRegistro{
    return DtoRegistro( id!!, fecha ,horaInicio, horaFin, tipoLibre)

}