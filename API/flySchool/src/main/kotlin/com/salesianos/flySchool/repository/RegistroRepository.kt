package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.RegistroVuelo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repositorio perteneciente a la entidad RegistroVuelo
 * @see RegistroVuelo
 */
@Repository
interface RegistroRepository : JpaRepository<RegistroVuelo, UUID> {

    /**
     * Query que busca el número de registros con una determinada aeronave
     */
    fun countByAeronave(aeronave: Aeronave): Int

    /**
     * Query que busca el número de registros pertenecientes a un determinado piloto
     */
    fun findByPiloto(piloto : Piloto): List<RegistroVuelo>

}