package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoFacturaAdmin
import com.salesianos.flySchool.dto.DtoFacturaCliente
import com.salesianos.flySchool.dto.toGetDtoFacturaAdmin
import com.salesianos.flySchool.dto.toGetDtoFacturaCliente
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.error.ListaAeronaveNotFoundException
import com.salesianos.flySchool.error.ListaFacturasNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.repository.AeronaveRepository
import com.salesianos.flySchool.repository.FacturaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class FacturaService(): BaseService<Factura, UUID, FacturaRepository>() {

    override fun save(fact: Factura) = super.save(fact)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    fun countByProducto(producto: Producto) = repository.countByProducto(producto)

    fun findByComprador(piloto: Piloto) = repository.findByComprador(piloto)

    fun litado(): ResponseEntity<List<DtoFacturaAdmin>> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findAll().map{it.toGetDtoFacturaAdmin()}
            .takeIf { it.isNotEmpty() } ?: throw ListaFacturasNotFoundException(Factura::class.java))
    }

    fun listadoUsuario(user: Usuario): ResponseEntity<List<DtoFacturaCliente>> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findByComprador(user as Piloto).map{it.toGetDtoFacturaCliente()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaFacturasNotFoundException(Factura::class.java))
    }

    fun crear(id: UUID,  user: Usuario, productoService: ProductoService, usuarioService: UsuarioService): ResponseEntity<DtoFacturaAdmin> {
        val producto = productoService.findById(id).orElseThrow{ ProductoSearchNotFoundException(id.toString()) }
        var factura = Factura(producto.precio, LocalDateTime.now(), user as Piloto, producto)
        this.save(factura)
        user.horas += producto.horasVuelo
        usuarioService.edit(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(factura.toGetDtoFacturaAdmin())

    }

}