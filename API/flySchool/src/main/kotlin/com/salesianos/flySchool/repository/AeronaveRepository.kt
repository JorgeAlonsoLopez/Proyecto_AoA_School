package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AeronaveRepository : JpaRepository<Aeronave, UUID> {

}