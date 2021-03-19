package com.salesianos.flyschool.poko

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class DtoRegistroForm(
        var horaInicio : String,
        var horaFin : String
)

data class DtoRegistro(
        val id: String,
        var fecha : LocalDate,
        var horaInicio : LocalTime,
        var horaFin : LocalTime,
        var tipoLibre : Boolean,
        var piloto: DtoPilot,
        var aeronave: DtoAeronavePeq
)
