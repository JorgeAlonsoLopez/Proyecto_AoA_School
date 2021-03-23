package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, UUID?> {

    fun findByUsername(username : String) : Optional<Usuario>

    @Query("SELECT u FROM Usuario u WHERE lower(u.nombreCompleto) LIKE %:nombre%")
    fun findByFullName(@Param("nombre")nombre : String): List<Usuario>?

}