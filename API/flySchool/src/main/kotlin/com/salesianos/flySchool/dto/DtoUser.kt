package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class DtoUserForm(
    @get:NotBlank(message="{usuario.username.blank}")
    var username : String,

    @get:NotBlank(message="{usuario.password.blank}")
    var password : String,

    @get:NotBlank(message="{usuario.email.blank}")
    @get:Email(message="{usuario.email.format}")
    var email : String,

    @get:NotBlank(message="{usuario.telefono.blank}")
    var telefono : String,

    @get:NotBlank(message="{usuario.nombreCompleto.blank}")
    var nombreCompleto : String,

    @get:NotNull(message="{usuario.fechaNacimiento.null}")
    var fechaNacimiento : String,

    var tarjetaCredito :  String
)

data class DtoUserInfo(
    var username : String,
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
    return DtoUserInfo(username, nombreCompleto, tipo, id!!)

}

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

fun Usuario.toGetDtoUserInfoSpeci():DtoUserInfoSpeci{
    return DtoUserInfoSpeci(id!!, username, password, email, telefono, nombreCompleto, fechaNacimiento, roles.first())

}

data class DtoLogin(
    var username: String,
    var password : String
)






