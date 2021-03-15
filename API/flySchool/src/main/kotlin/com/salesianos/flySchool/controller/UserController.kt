package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.AeronaveSearchNotFoundException
import com.salesianos.flySchool.error.ListaProductoNotFoundException
import com.salesianos.flySchool.error.ProductoModifNotFoundException
import com.salesianos.flySchool.service.PilotoService
import com.salesianos.flySchool.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@Controller
@RequestMapping("/")
class UserController(
        private val usuarioServicio: UsuarioService,
        private val encoder: PasswordEncoder,
        private val pilotoService: PilotoService
        ) {

    @PostMapping("auth/register")
    fun nuevoUsuario(@Valid @RequestBody newUser: DtoUserForm): ResponseEntity<DtoUserInfoSpeci> {

        lateinit var nuevoUsuario : Usuario
        var fecNac= LocalDate.of((newUser.fechaNacimiento.split("/")[2]).toInt(),(newUser.fechaNacimiento.split("/")[1]).toInt(),(newUser.fechaNacimiento.split("/")[0]).toInt())

        return usuarioServicio.create(newUser, fecNac).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toGetDtoUserInfoSpeci()) }.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${newUser.username} ya existe")
        }

    }

    @GetMapping("usuario/")
    fun listado() : ResponseEntity<List<DtoUserInfo>> {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.findAll().map{it.toGetDtoUserInfo(it)}
                .takeIf { it.isNotEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @GetMapping("usuario/{id}")
    fun detalle(@PathVariable id: UUID) : ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.findById(id).map { it.toGetDtoUserInfoSpeci() }
                .orElseThrow { AeronaveSearchNotFoundException(id.toString()) })
    }

    @PutMapping("usuario/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return ResponseEntity.status(HttpStatus.OK).body(pilotoService.findById(id)
                .map { prod ->
                    prod.alta = !prod.alta!!
                    pilotoService.save(prod).toGetDtoUserInfoSpeci()
                }.orElseThrow { ProductoModifNotFoundException(id.toString()) })
    }

    @PutMapping("usuario/{id}")
    fun editar(@Valid @RequestBody user: DtoUserForm, @PathVariable id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.findById(id)
                .map { fromRepo ->
                    fromRepo.email = user.email
                    fromRepo.nombreCompleto = user.nombreCompleto
                    fromRepo.telefono = user.telefono
                    fromRepo.fechaNacimiento = LocalDate.of((user.fechaNacimiento.split("/")[2]).toInt(),(user.fechaNacimiento.split("/")[1]).toInt(),(user.fechaNacimiento.split("/")[0]).toInt())
                    usuarioServicio.save(fromRepo).toGetDtoUserInfoSpeci()
                }.orElseThrow { ProductoModifNotFoundException(id.toString()) })
    }

    @PutMapping("usuario/password")
    fun editPassword(@RequestBody passw: DtoPassword,): Any {
        var user = Usuario("", "", "", "", "", LocalDate.now(), mutableSetOf(""))
        if(passw.password1 == passw.password2){
            user.password = passw.password1
            usuarioServicio.save(user)
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioServicio.findById(id)
                .map { fromRepo ->
                    fromRepo.email = user.email
                    fromRepo.nombreCompleto = user.nombreCompleto
                    fromRepo.telefono = user.telefono
                    fromRepo.fechaNacimiento = LocalDate.of((user.fechaNacimiento.split("/")[2]).toInt(),(user.fechaNacimiento.split("/")[1]).toInt(),(user.fechaNacimiento.split("/")[0]).toInt())
                    usuarioServicio.save(fromRepo).toGetDtoUserInfoSpeci()
                }.orElseThrow { ProductoModifNotFoundException(id.toString()) })
    }






}