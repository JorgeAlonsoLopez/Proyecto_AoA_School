package com.salesianos.flySchool.repository

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductoRepository : JpaRepository<Producto, UUID> {

}