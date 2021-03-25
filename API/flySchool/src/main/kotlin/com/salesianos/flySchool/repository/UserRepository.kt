package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Usuario
 * @see Usuario
 */
@Repository
interface UsuarioRepository : JpaRepository<Usuario, UUID?> {

    /**
     * Query que busca al objeto que posee un determinado nombre de usuario
     */
    fun findByUsername(username : String) : Optional<Usuario>

    /**
     * Query que busca todos los objetos cuyos nombres completos posean el string que se le pasa como parámetro
     */
    @Query("SELECT u FROM Usuario u WHERE lower(u.nombreCompleto) LIKE %:nombre%")
    fun findByFullName(@Param("nombre")nombre : String): List<Usuario>?

}