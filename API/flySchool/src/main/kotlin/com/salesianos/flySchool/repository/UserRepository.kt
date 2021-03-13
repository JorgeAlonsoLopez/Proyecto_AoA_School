package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, UUID?> {

    fun findByUsername(username : String) : Optional<Usuario>

}