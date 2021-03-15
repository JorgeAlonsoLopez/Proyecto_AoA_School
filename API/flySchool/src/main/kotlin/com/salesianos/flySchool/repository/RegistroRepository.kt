package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.RegistroVuelo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RegistroRepository : JpaRepository<RegistroVuelo, UUID> {
    fun countByAeronave(aeronave: Aeronave): Int
    fun findByPiloto(piloto : Piloto): List<RegistroVuelo>
}