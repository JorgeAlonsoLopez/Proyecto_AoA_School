package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Piloto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Piloto
 * @see Piloto
 */
@Repository
interface PilotoRepository : JpaRepository<Piloto, UUID> {
}