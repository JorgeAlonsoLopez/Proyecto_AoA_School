package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.NewUserException
import com.salesianos.flySchool.error.UserSearchNotFoundException
import com.salesianos.flySchool.service.PilotoService
import com.salesianos.flySchool.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
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

        return ResponseEntity.status(HttpStatus.CREATED).body(service.nuevoUsuario(newUser)?.orElseThrow {
            NewUserException(newUser.username) })

    }

    @GetMapping("usuario/")
    fun listado() : ResponseEntity<List<DtoUserInfo>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }

    @GetMapping("usuario/{id}")
    fun detalle(@PathVariable id: UUID) : ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(service.detalle(id))
    }

    @GetMapping("usuario/piloto/{id}")
    fun detallePiloto(@PathVariable id: UUID) : ResponseEntity<DtoPilot> {
        return ResponseEntity.status(HttpStatus.OK).body(service.detallePiloto(id))
    }

    @PutMapping("usuario/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id, pilotoService))
    }

    @PutMapping("usuario/{id}")
    fun editar(@Valid @RequestBody user: DtoUserEdit, @PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return  ResponseEntity.status(HttpStatus.OK).body(service.editar(user, id))
    }

    @PutMapping("usuario/password")
    fun editPassword(@RequestBody passw: DtoPassword,@AuthenticationPrincipal user: Usuario): ResponseEntity<Any> {
        if(service.editPassword(passw, user)){
            return ResponseEntity.status(HttpStatus.OK).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

    @GetMapping("usuario/me")
    fun me(@AuthenticationPrincipal user: Usuario): ResponseEntity<Any> {
        return ResponseEntity.ok().body(service.me(user))
    }


    @PutMapping("usuario/licencia/{id}")
    fun licencia(@PathVariable id: UUID): ResponseEntity<Any> {
        return ResponseEntity.ok().body(service.licencia(id))
    }

}