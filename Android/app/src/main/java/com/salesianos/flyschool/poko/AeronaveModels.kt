package com.salesianos.flyschool.poko

import java.util.*

data class DTOFotoUrl(
        var url : String,
        var hash : String
)

data class DtoAeronaveSinFoto(
        val id: String,
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

data class DtoAeronavePeq(
        val id: String,
        var matricula : String,
        var marca : String,
        var modelo : String,
        var mantenimiento : Boolean,
        var alta: Boolean
)


data class DtoAeronaveResp(
        val id:String,
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

data class DtoAeronaveForm(
        var matricula : String,
        var marca : String,
        var modelo : String,
        var motor : String,
        var potencia : Double,
        var autonomia : Double,
        var velMax : Double,
        var velMin : Double,
        var velCru : Double
)

