package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.error.ListaProductoNotFoundException
import com.salesianos.flySchool.error.ProductoModifNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.service.FacturaService
import com.salesianos.flySchool.service.ProductoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/factura")
class FacturaController(
        private val service: FacturaService,
        private val productoService: ProductoService
) {

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoFacturaAdmin>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll().map{it.toGetDtoFacturaAdmin()}
                .takeIf { it.isNotEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @GetMapping("/user")
    fun listadoUsuario() : ResponseEntity<List<DtoFacturaCliente>> {
        //TODO buscar logeado
        return ResponseEntity.status(HttpStatus.OK).body(service.findByComprador(piloto).map{it.toGetDtoFacturaCliente()}
                .takeIf { it.isNullOrEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @PostMapping("/{id}")
    fun crear(@PathVariable id: UUID) : ResponseEntity<DtoFacturaAdmin> {
        val producto = productoService.findById(id)
        if(producto!=null){
            var factura = Factura(producto.get().precio, LocalDateTime.now(), piloto, producto.get())
            service.save(factura)
            return ResponseEntity.status(HttpStatus.CREATED).body(factura.toGetDtoFacturaAdmin())
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }


    }


}