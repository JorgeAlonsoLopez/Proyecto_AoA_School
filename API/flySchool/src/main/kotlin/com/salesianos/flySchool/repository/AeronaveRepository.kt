package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Aeronave
 * @see Aeronave
 */
@Repository
interface AeronaveRepository : JpaRepository<Aeronave, UUID> {

    /**
     * Query que busca las aeronaves cuya propiedad alta se encuentre según se le pase
     */
    fun findByAlta(alta : Boolean): List<Aeronave>?

    /**
     * Query que busca las aeronaves que tengan la matrícula proporcionada
     */
    fun countByMatricula(matricula: String): Int

}