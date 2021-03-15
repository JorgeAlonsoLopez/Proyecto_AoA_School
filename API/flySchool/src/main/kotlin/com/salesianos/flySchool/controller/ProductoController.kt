package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.error.*
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

        var producto = Producto(nueva.nombre, nueva.precio, nueva.horasVuelo, nueva.tipoLibre)
        service.save(producto)
        return ResponseEntity.status(HttpStatus.CREATED).body(producto.toGetDtoProductoEspecf())
    }

    @PutMapping("/{id}")
    fun editar(@Valid @RequestBody editada: DtoProductoForm, @PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {

        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id)
            .map { fromRepo ->
                fromRepo.nombre = editada.nombre
                fromRepo.precio = editada.precio
                fromRepo.horasVuelo = editada.horasVuelo
                fromRepo.tipoLibre = editada.tipoLibre
                service.save(fromRepo).toGetDtoProductoEspecf()
            }.orElseThrow { ProductoModifNotFoundException(id.toString()) })
    }

    @PutMapping("/{id}/est")
    fun cambiarEstado(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {

        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id)
            .map { prod ->
                prod.alta = !prod.alta
                service.save(prod).toGetDtoProductoEspecf()
            }.orElseThrow { ProductoModifNotFoundException(id.toString()) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {

        val producto = service.findById(id).orElseThrow { ProductoSearchNotFoundException(id.toString()) }
        if(facturaService.countByProducto(producto) == 0 )
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body( service.delete(producto))
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll().map{it.toGetDtoProductoEspecf()}
            .takeIf { it.isNotEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @GetMapping("/alta/{licencia}")
    fun listadoAlta(@PathVariable licencia: Boolean) : ResponseEntity<List<DtoProductoEspecf>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllAlta(licencia)?.map{it.toGetDtoProductoEspecf()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @GetMapping("/{id}")
    fun aeronaveId(@PathVariable id: UUID): ResponseEntity<DtoProductoEspecf> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).map { it.toGetDtoProductoEspecf() }
            .orElseThrow { ProductoSearchNotFoundException(id.toString()) })
    }




}