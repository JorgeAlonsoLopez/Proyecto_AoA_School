package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.repository.AeronaveRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AeronaveService(
): BaseService<Aeronave, UUID, AeronaveRepository>()
{

    override fun save(aeronave: Aeronave) = super.save(aeronave)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

}