package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.*
import com.salesianos.flySchool.service.PilotoService
import com.salesianos.flySchool.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/")
class UserController(
    private val service: UsuarioService,
    private val pilotoService: PilotoService
        ) {

    @PostMapping("auth/register")
    fun nuevoUsuario(@Valid @RequestBody newUser: DtoUserForm): ResponseEntity<DtoUserInfoSpeci> {

        return service.nuevoUsuario(newUser)

    }

    @GetMapping("usuario/")
    fun listado() : ResponseEntity<List<DtoUserInfo>> {
        return service.listado()
    }

    @GetMapping("usuario/{id}")
    fun detalle(@PathVariable id: UUID) : ResponseEntity<DtoUserInfoSpeci> {
        return service.detalle(id)
    }

    @PutMapping("usuario/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {
        return service.cambiarEstado(id, pilotoService)
    }

    @PutMapping("usuario/{id}")
    fun editar(@Valid @RequestBody user: DtoUserForm, @PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return service.editar(user, id)
    }

    @PutMapping("usuario/password")
    fun editPassword(@RequestBody passw: DtoPassword,) {
        return service.editPassword(passw)
    }

    @GetMapping("usuario/me")
    fun me(@AuthenticationPrincipal user: Usuario): ResponseEntity<Any> {
        return service.me(user)
    }






}