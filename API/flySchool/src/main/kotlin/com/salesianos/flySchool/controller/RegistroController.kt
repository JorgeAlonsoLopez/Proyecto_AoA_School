package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/registro")
class RegistroController(
        private val service: RegistroService,
        private val aeronaveService: AeronaveService,
        private val pilotoService: PilotoService
) {

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoRegistro>> {
        return service.listado()
    }

    @GetMapping("/user")
    fun listadoUsuario(@AuthenticationPrincipal user: Usuario) : ResponseEntity<List<DtoRegistro>> {
        return service.listado()
    }

    @PostMapping("/{id}")
    fun crear(@RequestBody nueva: DtoRegistroForm, @PathVariable id: UUID, @AuthenticationPrincipal user: Usuario) : ResponseEntity<DtoRegistro> {
        return service.crear(nueva, id, user, aeronaveService, pilotoService)

    }

}