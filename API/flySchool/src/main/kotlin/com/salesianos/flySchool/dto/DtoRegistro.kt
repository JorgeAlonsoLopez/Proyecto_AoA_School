package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.RegistroVuelo
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
        var tipoLibre : Boolean,
        var piloto: DtoPilot,
        var aeronave: Aeronave
)

fun RegistroVuelo.toGetDtoRegistro():DtoRegistro{
    return DtoRegistro( id!!, fecha ,horaInicio, horaFin, tipoLibre, piloto.toGetDtoUserInfoSpeciPilot(), aeronave)

}