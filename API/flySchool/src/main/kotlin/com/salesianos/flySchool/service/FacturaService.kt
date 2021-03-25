package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoFacturaAdmin
import com.salesianos.flySchool.dto.DtoFacturaCliente
import com.salesianos.flySchool.dto.toGetDtoFacturaAdmin
import com.salesianos.flySchool.dto.toGetDtoFacturaCliente
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.error.ListaFacturasNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.repository.FacturaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

/**
 * Servicio perteneciente a la entidad Factura, heredando de BaseService
 * @see Factura
 */
@Service
class FacturaService(): BaseService<Factura, UUID, FacturaRepository>() {

    override fun save(fact: Factura) = super.save(fact)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    /**
     * Cuenta el número de facturas que están relacionadas con un producto
     * @param producto Producto con el que se va a filtrar
     * @return número de facturas asociadas a un producto
     */
    fun countByProducto(producto: Producto) = repository.countByProducto(producto)

    /**
     * Obtienen las facturas pertenecientes a un piloto
     * @param piloto Piloto con el que se va a filtrar
     * @return listado de facturas por usuario
     */
    fun findByComprador(piloto: Piloto) = repository.findByComprador(piloto)

    /**
     * Función que obtiene un listado de todas las facturas, lanzando la excepción correspondiente en el caso de que no se encuntre ninguna
     * @return listado de Dto de todas las facturas
     */
    fun litado(): List<DtoFacturaAdmin> {
        return this.findAll().map{it.toGetDtoFacturaAdmin()}
            .takeIf { it.isNotEmpty() } ?: throw ListaFacturasNotFoundException(Factura::class.java)
    }

    /**
     * Función que obtiene un listado de todas las facturas pertenecientes a un piloto,
     * lanzando la excepción correspondiente en el caso de que no se encuntre ninguna
     * @param user Usuario al que le pertenecen las facturas
     * @return lisado de Dto de facturas por usuario
     */
    fun listadoUsuario(user: Usuario): List<DtoFacturaCliente> {
        return this.findByComprador(user as Piloto).map{it.toGetDtoFacturaCliente()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaFacturasNotFoundException(Factura::class.java)
    }

    /**
     * Función que crea y guarda una factura
     * @param id Id del producto comprado
     * @param user Usuario que ha llevado a cabo la operación
     * @param productoService Servicio de la entidad Producto
     * @param usuarioService Servicio de la entidad Usuario
     * @return Dto de la factura creada
     */
    fun crear(id: UUID,  user: Usuario, productoService: ProductoService, usuarioService: UsuarioService): DtoFacturaAdmin {
        val producto = productoService.findById(id).orElseThrow{ ProductoSearchNotFoundException(id.toString()) }
        var factura = Factura(producto.precio, LocalDateTime.now(), user as Piloto, producto)
        this.save(factura)
        user.horas += producto.horasVuelo
        usuarioService.edit(user)
        return factura.toGetDtoFacturaAdmin()
    }

}