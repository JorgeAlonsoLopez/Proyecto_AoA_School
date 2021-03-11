package com.salesianostriana.flySchool.dto

import com.salesianostriana.flySchool.entity.Piloto
import com.salesianostriana.flySchool.entity.Usuario
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class DtoPilotoForm(
    @get:NotBlank(message="{usuario.username.blank}")
    var usuario : String,

    @get:NotBlank(message="{usuario.password.blank}")
    var password : String,

    @get:NotBlank(message="{usuario.email.blank}")
    var email : String,

    @get:NotBlank(message="{usuario.telefono.blank}")
    var telefono : String,

    @get:NotBlank(message="{usuario.nombreCompleto.blank}")
    var nombreCompleto : String,

    @get:NotNull(message="{usuario.fechaNacimiento.null}")
    var fechaNacimiento : String,

    @get:NotBlank(message="{usuario.tarjeta.blank}")
    var tarjetaCredito :  String
)

data class DtoAdminForm(
    @get:NotBlank(message="{usuario.username.blank}")
    var usuario : String,

    @get:NotBlank(message="{usuario.password.blank}")
    var password : String,

    @get:NotBlank(message="{usuario.email.blank}")
    var email : String,

    @get:NotBlank(message="{usuario.telefono.blank}")
    var telefono : String,

    @get:NotBlank(message="{usuario.nombreCompleto.blank}")
    var nombreCompleto : String,

    @get:NotNull(message="{usuario.fechaNacimiento.null}")
    var fechaNacimiento : String
)

data class DtoUserInfo(
    var usuario : String,
    var nombreCompleto : String,
    var tipo: String,
    var id:UUID
)

fun Usuario.toGetDtoUserInfo(obj : Usuario):DtoUserInfo{
    var tipo = ""
    if(obj is Piloto){
        tipo = "Piloto"
    }else{
        tipo = "Admin"
    }
    return DtoUserInfo(usuario, nombreCompleto, tipo, id!!)

}

data class DtoUserInfoSpeci(
    val id:UUID,
    var usuario : String,
    var password : String,
    var email : String,
    var telefono : String,
    var nombreCompleto : String,
    var fechaNacimiento : LocalDate
)

fun Usuario.toGetDtoUserInfoSpeciDtoUserInfo():DtoUserInfoSpeci{
    return DtoUserInfoSpeci(id!!, usuario, password, email, telefono, nombreCompleto, fechaNacimiento)

}





