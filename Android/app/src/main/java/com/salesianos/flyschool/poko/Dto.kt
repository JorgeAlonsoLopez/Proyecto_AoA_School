package com.salesianos.flyschool.poko

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

data class DtoRegistroForm(
    var horaInicio : String,
    var horaFin : String
)

data class DtoRegistro(
    val id: UUID,
    var fecha : LocalDate,
    var horaInicio : LocalTime,
    var horaFin : LocalTime,
    var tipoLibre : Boolean,
    var piloto: DtoPilot,
    var aeronave: DtoAeronavePeq
)

data class DtoUserEdit(
    var email : String,
    var telefono : String,
    var nombreCompleto : String,
    var fechaNacimiento : String
)

data class DtoPassword(
    var password1 : String,
    var password2 : String
)

data class DtoPilot(
    val id:UUID,
    var username : String,
    var password : String,
    var email : String,
    var telefono : String,
    var nombreCompleto : String,
    var fechaNacimiento : LocalDate,
    var horas: Double,
    var licencia: Boolean,
    var alta: Boolean

)

data class DtoUserInfoSpeci(
    val id:UUID,
    var username : String,
    var password : String,
    var email : String,
    var telefono : String,
    var nombreCompleto : String,
    var fechaNacimiento : LocalDate,
    var rol : String
)

data class DtoUserForm(
    var username : String,
    var password : String,
    var email : String,
    var telefono : String,
    var nombreCompleto : String,
    var fechaNacimiento : String,
    var tarjetaCredito :  String
)

data class DtoUserInfo(
    var username : String,
    var nombreCompleto : String,
    var tipo: String,
    var id:UUID,
    var alta: Boolean
)

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

data class DTOFotoUrl(
    var url : String,
    var hash : String
)

data class DtoAeronaveSinFoto(
    val id:UUID,
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
    val id:UUID,
    var matricula : String,
    var marca : String,
    var modelo : String,
    var mantenimiento : Boolean,
    var alta: Boolean
)

data class DtoAeronaveResp(
    val id:UUID,
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

data class DtoFacturaCliente(

    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: DtoProductoEspecf,
    var tipo: Boolean
)

data class DtoFacturaAdmin(

    val id:UUID,
    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: DtoProductoEspecf,
    var comprador: DtoPilot,
    var tipo: Boolean
)