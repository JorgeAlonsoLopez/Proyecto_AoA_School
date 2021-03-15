package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AeronaveRepository : JpaRepository<Aeronave, UUID> {

    @Query(value = "SELECT * FROM Aeronave a WHERE a.alta = true", nativeQuery = true)
    fun findAllByAlta() {
    }

    fun findByAlta(alta : Boolean): List<Aeronave>?

}