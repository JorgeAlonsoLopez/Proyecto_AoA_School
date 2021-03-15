package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/producto")
class ProductoController(
    private val service: ProductoService,
    private val facturaService: FacturaService
) {

    @PostMapping("/")
    fun crear(@Valid @RequestBody nueva: DtoProductoForm) : ResponseEntity<DtoProductoEspecf> {
       return service.crear(nueva)
    }

    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody editada: DtoProductoForm, @PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return service.editar(editada, id)
    }

    @PutMapping("/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return service.cambiarEstado(id)
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: UUID): ResponseEntity<Unit> {
        return service.eliminar(id, facturaService)
    }

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoProductoEspecf>> {
        return service.listado()
    }

    @GetMapping("/alta/{licencia}")
    fun listadoAlta(@PathVariable licencia: Boolean) : ResponseEntity<List<DtoProductoEspecf>> {
        return service.listadoAlta(licencia)
    }

    @GetMapping("/{id}")
    fun productoId(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return service.productoId(id)
    }




}