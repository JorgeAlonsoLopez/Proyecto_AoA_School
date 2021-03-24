package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoProductoEspecf
import com.salesianos.flySchool.dto.DtoProductoForm
import com.salesianos.flySchool.dto.toGetDtoProductoEspecf
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.error.ListaProductoNotFoundException
import com.salesianos.flySchool.error.ProductoModifNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.repository.ProductoRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Servicio perteneciente a la clase Producto
 * @see Producto
 */
@Service
class ProductoService(
): BaseService<Producto, UUID, ProductoRepository>()
{

    override fun save(produc: Producto) = super.save(produc)

    override fun findAll() = super.findAll()

    /**
     * Busca todas las entidades que estén dadas de alta y sean o no (dependiendo de la ocasión) de tipo libre o eseñanza
     */
    fun findAllAlta(opt : Boolean) = repository.findByAltaAndTipoLibre(true, opt)

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    /**
     * Función donde a partir de un Dto crea una entidad de tipo producto y la guarda en la base de datos
     */
    fun crear(nueva: DtoProductoForm): DtoProductoEspecf {
        var producto = Producto(nueva.nombre, nueva.precio, nueva.horasVuelo, nueva.tipoLibre)
        this.save(producto)
        return producto.toGetDtoProductoEspecf()
    }

    /**
     * Función donde a partir de un Dto edita una entidad de tipo producto y la guarda en la base de datos.
     * En caso de no encontrarla, lanza la excepción correspondiente
     */
    fun editar(editada: DtoProductoForm, id: UUID): DtoProductoEspecf? {
        return this.findById(id)
            .map { fromRepo ->
                fromRepo.nombre = editada.nombre
                fromRepo.precio = editada.precio
                fromRepo.horasVuelo = editada.horasVuelo
                fromRepo.tipoLibre = editada.tipoLibre
                this.save(fromRepo).toGetDtoProductoEspecf()
            }.orElseThrow { ProductoModifNotFoundException(id.toString()) }
    }

    /**
     * Función donde a partir de un Dto edita una entidad de tipo producto y la guarda en la base de datos.
     * En caso de no encontrarla, lanza la excepción correspondiente
     */
    fun cambiarEstado(id : UUID): DtoProductoEspecf {
        return this.findById(id)
            .map { prod ->
                prod.alta = !prod.alta
                this.save(prod).toGetDtoProductoEspecf()
            }.orElseThrow { ProductoModifNotFoundException(id.toString()) }
    }

    /**
     * Función que elimina una entidad de tipo producto, borrándola de la base de datos.
     * En caso de no encontrarla, lanza la excepción correspondiente
     */
    fun eliminar(id : UUID,  facturaService: FacturaService): Int {
        val producto = this.findById(id).orElseThrow { ProductoSearchNotFoundException(id.toString()) }
        if(facturaService.countByProducto(producto) == 0 ) {
            this.delete(producto)
            return 1
        }else {
            return 0
        }
    }

    /**
     * Función donde se listan todas las entidades.
     * En caso de no encontrar nada, lanza la excepción correspondiente
     */
    fun listado(): List<DtoProductoEspecf> {
        return this.findAll().map{it.toGetDtoProductoEspecf()}
            .takeIf { it.isNotEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java)
    }

    /**
     * Función donde se listan todas las entidades dadas de alta y con la propiedad licencia según corresponda.
     * En caso de no encontrar nada, lanza la excepción correspondiente
     */
    fun listadoAlta(licencia: Boolean): List<DtoProductoEspecf> {
        return this.findAllAlta(licencia)?.map{it.toGetDtoProductoEspecf()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java)
    }

    /**
     * Función donde busca una entidad por su ID.
     * En caso de no encontrarla, lanza la excepción correspondiente
     */
    fun productoId(id : UUID): DtoProductoEspecf? {
        return this.findById(id).map { it.toGetDtoProductoEspecf() }
            .orElseThrow { ProductoSearchNotFoundException(id.toString()) }
    }

}