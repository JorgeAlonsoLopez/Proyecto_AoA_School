package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.repository.PilotoRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Servicio perteneciente a la clase Piloto
 * @see Piloto
 */
@Service
class PilotoService (
): BaseService<Piloto, UUID, PilotoRepository>()
{

    override fun save(piloto: Piloto) = super.save(piloto)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

}