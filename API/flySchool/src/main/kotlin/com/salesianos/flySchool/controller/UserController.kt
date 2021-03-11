package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.DtoUserForm
import com.salesianos.flySchool.dto.DtoUserInfoSpeci
import com.salesianos.flySchool.dto.toGetDtoUserInfoSpeci
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Controller
@RequestMapping("/auth")
class UserController(val usuarioServicio: UsuarioService, private val encoder: PasswordEncoder) {

    @PostMapping("/register")
    fun nuevoUsuario(@RequestBody newUser: DtoUserForm): ResponseEntity<DtoUserInfoSpeci> {

        lateinit var nuevoUsuario : Usuario
        var fecNac= LocalDate.of((newUser.fechaNacimiento.split("/")[2]).toInt(),(newUser.fechaNacimiento.split("/")[1]).toInt(),(newUser.fechaNacimiento.split("/")[0]).toInt())

        return usuarioServicio.create(newUser, fecNac).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toGetDtoUserInfoSpeci()) }.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${newUser.username} ya existe")
        }

    }
}