package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import org.springframework.http.HttpStatus
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
       return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(nueva))
    }

    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody editada: DtoProductoForm, @PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.editar(editada, id))
    }

    @PutMapping("/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(id))
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: UUID): ResponseEntity<Unit> {
        var resl = service.eliminar(id, facturaService)
        if(resl != 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listado())
    }

    @GetMapping("/alta/{licencia}")
    fun listadoAlta(@PathVariable licencia: Boolean) : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.listadoAlta(licencia))
    }

    @GetMapping("/{id}")
    fun productoId(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.productoId(id))
    }




}