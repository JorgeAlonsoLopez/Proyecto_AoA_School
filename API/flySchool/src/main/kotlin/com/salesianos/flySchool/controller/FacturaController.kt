package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.ListaFacturasNotFoundException
import com.salesianos.flySchool.error.ListaProductoNotFoundException
import com.salesianos.flySchool.error.ProductoModifNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import com.salesianos.flySchool.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/factura")
class FacturaController(
        private val service: FacturaService,
        private val productoService: ProductoService,
        private val usuarioService: UsuarioService
) {

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoFacturaAdmin>> {
        return service.litado()
    }

    @GetMapping("/user")
    fun listadoUsuario(@AuthenticationPrincipal user: Usuario) : ResponseEntity<List<DtoFacturaCliente>> {
        return service.listadoUsuario(user)
    }

    @PostMapping("/{id}")
    fun crear(@PathVariable id: UUID, @AuthenticationPrincipal user: Usuario) : ResponseEntity<DtoFacturaAdmin> {
        return service.crear(id, user, productoService, usuarioService)


    }


}