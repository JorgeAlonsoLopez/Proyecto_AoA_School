package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.FotoAeronave
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad FotoAeronave
 * @see FotoAeronave
 */
@Repository
interface FotoRepository : JpaRepository<FotoAeronave, UUID> {

}