package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Producto
 * @see Producto
 */
@Repository
interface ProductoRepository : JpaRepository<Producto, UUID> {

    /**
     * Query que busca todos todos los productos con un estado y tipo determinados.
     * Será el posterior filtro para mostrar a un detemrinado piloto que productos puede comprar
     */
    fun findByAltaAndTipoLibre(alta : Boolean, tipoLibre : Boolean): List<Producto>?

}