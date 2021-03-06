package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad Admin
 * @see Admin
 */
@Repository
interface AdminRepository : JpaRepository<Admin, UUID> {
}