package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.ApiError
import com.salesianos.flySchool.error.NewUserException
import com.salesianos.flySchool.security.jwt.JwtTokenProvider
import com.salesianos.flySchool.security.jwt.MensajeError
import com.salesianos.flySchool.service.PilotoService
import com.salesianos.flySchool.service.UsuarioService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/")
class UserController(
    private val service: UsuarioService
        ) {

    @ApiOperation(
        value = "Realizar el registro de un usuario",
        notes = "Se aportan los datos provenientes del formulario de registro"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 201, message = "Created", response = DtoUserInfoSpeci::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Bad credentials", response = MensajeError::class),
            ApiResponse(code = 404, message = "El nombre de usuario ya está registrado", response = ApiError::class)
        ]
    )
    @PostMapping("auth/register")
    fun nuevoUsuario(
        @Valid @RequestBody
        @ApiParam(value = "Los datos del registro", required = true)
        newUser: DtoUserForm)

    : ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.nuevoUsuario(newUser)?.orElseThrow {
            NewUserException(newUser.username) })
    }

    @ApiOperation(
        value = "Listado de todos los usuarios",
        notes = "Se obtinene un listado de todos los usuarios almacenados en la base de datos"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfo::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
        ]
    )
    @GetMapping("usuario/")
    fun listado() : ResponseEntity<List<DtoUserInfo>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }

    @ApiOperation(
        value = "Busca un usuario por ID",
        notes = "Busca a un usuario por su ID, devolviendo la información del usuario"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfoSpeci::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("usuario/{id}")
    fun detalle(
            @PathVariable
            @ApiParam(value = "ID del usuario a buscar", required = true)
            id: UUID) : ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(service.detalle(id))
    }

    @ApiOperation(
        value = "Busca un piloto por ID",
        notes = "Busca a un piloto por su ID, devolviendo la información del usuario"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoPilot::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("usuario/piloto/{id}")
    fun detallePiloto(@PathVariable
                      @ApiParam(value = "ID del usuario a buscar", required = true)
                      id: UUID) : ResponseEntity<DtoPilot> {
        return ResponseEntity.status(HttpStatus.OK).body(service.detallePiloto(id))
    }

    @ApiOperation(
        value = "Cambia el estado del piloto",
        notes = "Cambia la propiedad alta del piloto, buscándolo por ID, entre true y false"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfoSpeci::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @PutMapping("usuario/{id}/est")
    fun cambiarEstado(@PathVariable
                      @ApiParam(value = "ID del piloto a cambiar", required = true)
                      id: UUID): ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id))
    }

    @ApiOperation(
        value = "Realizar la modificación de los datos de un usuario",
        notes = "Se aportan los datos provenientes del formulario de edición, junto con el ID del usuario a modificar"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfoSpeci::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Bad credentials", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @PutMapping("usuario/{id}")
    fun editar(@Valid @RequestBody
               @ApiParam(value = "Datos del usuario que se quieren cambiar", required = true)
               user: DtoUserEdit,
               @PathVariable
               @ApiParam(value = "ID del usuario a modificar", required = true)
               id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return  ResponseEntity.status(HttpStatus.OK).body(service.editar(user, id))
    }

    @ApiOperation(
        value = "Realizar el cambio de contraseña",
        notes = "Se aporta la contraseña que se desea establecer"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = Any::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class)
        ]
    )
    @PutMapping("usuario/password")
    fun editPassword(@RequestBody
                     @ApiParam(value = "Contraseña y contraseña de confimación que se quiere establecer para la cuenta", required = true)
                     passw: DtoPassword,@AuthenticationPrincipal user: Usuario): ResponseEntity<Any> {
        if(service.editPassword(passw, user)){
            return ResponseEntity.status(HttpStatus.OK).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

    @ApiOperation(
        value = "Información del usuario logeado",
        notes = "Se obtiene la información de usuario que se ha logeado"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfoSpeci::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class)
        ]
    )
    @GetMapping("usuario/me")
    fun me(@AuthenticationPrincipal user: Usuario): ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.ok().body(service.me(user))
    }


    @ApiOperation(
        value = "Habilitar licencia",
        notes = "Se habilita la licencia de vuelo prar un determinado piloto"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = Any::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @PutMapping("usuario/licencia/{id}")
    fun licencia(@PathVariable
                 @ApiParam(value = "ID del piloto al que habilitar la licencia", required = true)
                 id: UUID): ResponseEntity<Any> {
        return ResponseEntity.ok().body(service.licencia(id))
    }

    @ApiOperation(
        value = "Filtro por nombre",
        notes = "Se filtran los usuario para tener un listado donde sus nombres completos coincidan con lo establecido"
    )
    @ApiResponses(

        value = [
            ApiResponse(code = 200, message = "OK", response = DtoUserInfo::class),
            ApiResponse(code = 400, message = "Bad request", response = ApiError::class),
            ApiResponse(code = 401, message = "Unauthorized", response = MensajeError::class),
            ApiResponse(code = 403, message = "Forbidden", response = MensajeError::class),
            ApiResponse(code = 404, message = "Not Found", response = ApiError::class)
        ]
    )
    @GetMapping("usuario/filtro")
    fun filtro(@RequestParam
               @ApiParam(value = "Parte del nombre completo del usuario que se desea encontrar", required = true)
               name: String): ResponseEntity<List<DtoUserInfo>> {
        return ResponseEntity.ok().body(service.listadoFiltroNombre(name))
    }

}