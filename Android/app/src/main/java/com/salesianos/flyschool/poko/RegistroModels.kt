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
        var fecha : String,
        var horaInicio : String,
        var horaFin : String,
        var tipoLibre : Boolean,
        var piloto: DtoPilot,
        var aeronave: DtoAeronavePeq
)
