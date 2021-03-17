package com.salesianos.flyschool.poko

import java.time.LocalDate
import java.util.*

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
        val id: UUID,
        var username : String,
        var password : String,
        var email : String,
        var telefono : String,
        var nombreCompleto : String,
        var fechaNacimiento : String,
        var horas: Double,
        var licencia: Boolean,
        var alta: Boolean

)

data class DtoUserInfoSpeci(
        val id: UUID,
        var username : String,
        var password : String,
        var email : String,
        var telefono : String,
        var nombreCompleto : String,
        var fechaNacimiento : String,
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
        var id: UUID,
        var alta: Boolean
)

data class DtoLogin(
        var username: String,
        var password: String
)

data class LoginResponse(
        val token: String,
        val user : DtoUserInfo
)