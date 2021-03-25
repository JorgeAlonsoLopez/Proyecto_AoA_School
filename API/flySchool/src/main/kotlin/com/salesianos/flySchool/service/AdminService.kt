package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.repository.AdminRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Servicio perteneciente a la entidad Admin, heredando de BaseService
 * @see Admin
 */
@Service
class AdminService (
): BaseService<Admin, UUID, AdminRepository>()
{
    override fun save(admin: Admin) = super.save(admin)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)



}