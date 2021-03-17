package com.salesianos.flyschool.poko

import java.util.*

data class DtoProductoForm(
        var tipoLibre : Boolean,
        var nombre : String,
        var precio : Double,
        var horasVuelo : Int
)

data class DtoProductoEspecf(
        val id: UUID,
        var tipoLibre : Boolean,
        var nombre : String,
        var precio : Double,
        var horasVuelo : Int,
        var alta : Boolean
)