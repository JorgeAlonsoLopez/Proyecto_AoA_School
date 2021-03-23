package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Clase que guarda la información proveniente del formulario de creación del usuario
 */
data class DtoUserForm(

    @ApiModelProperty(value = "Nombre de usuario")
    @get:NotBlank(message="{usuario.username.blank}")
    var username : String,

    @ApiModelProperty(value = "Contraseña")
    @get:NotBlank(message="{usuario.password.blank}")
    var password : String,

    @ApiModelProperty(value = "Email del usuario")
    @get:NotBlank(message="{usuario.email.blank}")
    @get:Email(message="{usuario.email.format}")
    var email : String,

    @ApiModelProperty(value = "Número de teléfono del usuario")
    @get:NotBlank(message="{usuario.telefono.blank}")
    var telefono : String,

    @ApiModelProperty(value = "Nombre completo del usuario")
    @get:NotBlank(message="{usuario.nombreCompleto.blank}")
    var nombreCompleto : String,

    @ApiModelProperty(value = "Fecha de nacimineto del usuario")
    @get:NotNull(message="{usuario.fechaNacimiento.null}")
    var fechaNacimiento : String,

    @ApiModelProperty(value = "Tarjeta de crédito del usuario. En el caso de que sea administrador, la propiedad estará vacía")
    var tarjetaCredito :  String
)

/**
 * Clase que guarda la información básica proveniente de la base de datos sobre un usuario en particular
 */
data class DtoUserInfo(

    @ApiModelProperty(value = "Nombre de usuario")
    var username : String,

    @ApiModelProperty(value = "Nombre completo del usuario")
    var nombreCompleto : String,

    @ApiModelProperty(value = "Tipo de usuario: piloto o administrador")
    var tipo: String,

    @ApiModelProperty(value = "ID del usuario")
    var id:UUID,

    @ApiModelProperty(value = "Estado del usuario")
    var alta: Boolean
)

fun Usuario.toGetDtoUserInfo(obj : Usuario):DtoUserInfo{
    var tipo = ""
    if(obj is Piloto){
        tipo = "Piloto"
        return DtoUserInfo(username, nombreCompleto, tipo, id!!, obj.alta)
    }else{
        tipo = "Admin"
        return DtoUserInfo(username, nombreCompleto, tipo, id!!, true)
    }


}

/**
 * Clase que guarda la información concreta proveniente de la base de datos sobre un usuario en particular
 */
data class DtoUserInfoSpeci(

    @ApiModelProperty(value = "ID del usuario")
    val id:UUID,

    @ApiModelProperty(value = "Nombre de usuario")
    var username : String,

    @ApiModelProperty(value = "Contraseña")
    var password : String,

    @ApiModelProperty(value = "Email del usuario")
    var email : String,

    @ApiModelProperty(value = "Número de teléfono del usuario")
    var telefono : String,

    @ApiModelProperty(value = "Nombre completo del usuario")
    var nombreCompleto : String,

    @ApiModelProperty(value = "Fecha de nacimineto del usuario")
    var fechaNacimiento : LocalDate,

    @ApiModelProperty(value = "Rol del usuario: piloto o administrador")
    var rol : String
)

fun Usuario.toGetDtoUserInfoSpeci():DtoUserInfoSpeci{
    return DtoUserInfoSpeci(id!!, username, password, email, telefono, nombreCompleto, fechaNacimiento, roles.first())

}

/**
 * Clase que guarda la información para cambiar la contraseña de un usuario, ambas contraseñas deben ser idénticas
 */
data class DtoPassword(

    @ApiModelProperty(value = "Primera ontraseña")
    var password1 : String,

    @ApiModelProperty(value = "Segunda contraseña")
    var password2 : String
)

/**
 * Clase que guarda la información concreta proveniente de la base de datos sobre un piloto en particular
 */
data class DtoPilot(

    @ApiModelProperty(value = "ID del usuario")
    val id:UUID,

    @ApiModelProperty(value = "Nombre de usuario")
    var username : String,

    @ApiModelProperty(value = "Contraseña")
    var password : String,

    @ApiModelProperty(value = "Email del usuario")
    var email : String,

    @ApiModelProperty(value = "Número de teléfono del usuario")
    var telefono : String,

    @ApiModelProperty(value = "Nombre completo del usuario")
    var nombreCompleto : String,

    @ApiModelProperty(value = "Fecha de nacimineto del usuario")
    var fechaNacimiento : LocalDate,

    @ApiModelProperty(value = "Tarjeta de crédito del usuario. En el caso de que sea administrador, la propiedad estará vacía")
    var tarjeta : String,

    @ApiModelProperty(value = "Número de horas que tiene el piloto disponibles para volar")
    var horas: Double,

    @ApiModelProperty(value = "Si el piloto tiene o no la licencia de vuelo. Si la tiene, el valor es true")
    var licencia: Boolean,

    @ApiModelProperty(value = "Estado del usuario")
    var alta: Boolean

)

fun Piloto.toGetDtoUserInfoSpeciPilot():DtoPilot{
    return DtoPilot(id!!, username, password, email, telefono, nombreCompleto, fechaNacimiento, tarjetaCredito ,horas, licencia, alta)

}

/**
 * Clase que guarda la información proveniente del formulario de edición de un usuario
 */
data class DtoUserEdit(

    @ApiModelProperty(value = "Email del usuario")
    var email : String,

    @ApiModelProperty(value = "Número de teléfono del usuario")
    var telefono : String,

    @ApiModelProperty(value = "Nombre completo del usuario")
    var nombreCompleto : String,

    @ApiModelProperty(value = "Fecha de nacimineto del usuario")
    var fechaNacimiento : String,

    @ApiModelProperty(value = "Tarjeta de crédito del usuario. En el caso de que sea administrador, la propiedad estará vacía")
    var tarjeta : String
)

