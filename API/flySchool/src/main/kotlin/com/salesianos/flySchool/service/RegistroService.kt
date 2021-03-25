package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoRegistro
import com.salesianos.flySchool.dto.DtoRegistroForm
import com.salesianos.flySchool.dto.toGetDtoRegistro
import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.error.AeronaveSearchNotFoundException
import com.salesianos.flySchool.error.ListaRegistroVueloNotFoundException
import com.salesianos.flySchool.repository.RegistroRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * Servicio perteneciente a la clase RegistroVuelo
 * @see RegistroVuelo
 */
@Service
class RegistroService (): BaseService<RegistroVuelo, UUID, RegistroRepository>(){

    override fun save(registro: RegistroVuelo) = super.save(registro)

    override fun findAll() = super.findAll()

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    /**
     * Obtiene el número de registros de vuelos que se han realizado con una determinada aeronave
     * @param aeronave Aeronave que sirve de filtro
     * @return número de registros asociados
     */
    fun countByAeronave(aeronave: Aeronave) = repository.countByAeronave(aeronave)

    /**
     * Obtiene todos los registros de vuelos que ha realizado un piloto
     * @param piloto Piloto que sirve de filtro
     * @return número de registros asociados al piloto
     */
    fun findByPiloto(piloto: Piloto) = repository.findByPiloto(piloto)

    /**
     * Función que obtiene la diferencia entre dos momentos de tiempo
     * @param start Tiempo de inicio
     * @param stop Tiempo de fin
     * @return LocalTime de la diferencia
     */
    fun difference(start: LocalTime, stop: LocalTime): LocalTime {
        lateinit var end : LocalTime
        var min = stop.minute - start.minute
        var hor = 0
        if(min < 0){
            hor = stop.hour - start.hour - 1
            min = (60+min)
        }else{
            hor = stop.hour - start.hour
        }
        end = LocalTime.of(hor, min)
        return end
    }

    /**
     * Obtiene el listado de todos los registros de vuelos y lanza una determinada excepción en el caso de no haber ninguno
     * @return listado de Dto de los registros
     */
    fun listado() : List<DtoRegistro> {
        return this.findAll().map{it.toGetDtoRegistro()}
            .takeIf { it.isNotEmpty() } ?: throw ListaRegistroVueloNotFoundException(RegistroVuelo::class.java)
    }

    /**
     * Obtiene el listado de todos los registros de vuelos realizados por un piloto y lanza una determinada excepción en el caso de no haber ninguno
     * @param user Usuario al que le pertenecen los registros
     * @return listado de Dto de los registros por usuarios
     */
    fun listadoUsuario(user: Usuario) : List<DtoRegistro> {
        return this.findByPiloto(user as Piloto).map{it.toGetDtoRegistro()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaRegistroVueloNotFoundException(RegistroVuelo::class.java)
    }

    /**
     * Crea un registro nuevo aportándole el piloto, las horas de inicio y fin y la aeronave usada
     * @param nueva Dto con los datos del registro
     * @param id Id de la aeronave utilizada
     * @param user Uusario que ha llevado a cabo el registro
     * @param aeronaveService Servicio de la entidad Aeronave
     * @param pilotoService Servicio de la entidad Piloto
     * @return Dto del registro creado
     */
    fun crear(nueva: DtoRegistroForm, id: UUID, user: Usuario, aeronaveService: AeronaveService, pilotoService: PilotoService) : DtoRegistro? {
        val piloto = user as Piloto
        val aeronave = aeronaveService.findById(id).orElseThrow{ AeronaveSearchNotFoundException(id.toString()) }
        val inicio = LocalTime.of(nueva.horaInicio.split(":")[0].toInt(),nueva.horaInicio.split(":")[1].toInt())
        val fin =LocalTime.of(nueva.horaFin.split(":")[0].toInt(),nueva.horaFin.split(":")[1].toInt())
        if(inicio < fin && aeronave != null){
            var registro = RegistroVuelo(
                LocalDate.now(),inicio, fin,
                this.difference(inicio, fin), piloto, aeronave, piloto.licencia!!)
            this.save(registro)
            var tiemp : Double = registro.tiempo.hour.toDouble() + (registro.tiempo.minute.toDouble()/60)
            piloto.horas = piloto.horas!! - tiemp
            pilotoService.save(piloto)
            return registro.toGetDtoRegistro()
        }else{
            return null
        }

    }

}