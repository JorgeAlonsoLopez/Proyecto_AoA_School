package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.FotoAeronave
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.*
import com.salesianos.flySchool.repository.AeronaveRepository
import com.salesianos.flySchool.upload.ImgurBadRequest
import com.salesianos.flySchool.upload.ImgurStorageService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

/**
 * Servicio perteneciente a la entidad Aeronave, heredando de BaseService
 * @see Aeronave
 */
@Service
class AeronaveService(
): BaseService<Aeronave, UUID, AeronaveRepository>()
{

    override fun save(aeronave: Aeronave) = super.save(aeronave)

    override fun findAll() = super.findAll()

    /**
     * Método que busca todos los objetos de tipo Aeronave dados de alta
     * @return Listado de aeronaves que pasan el filtro
     */
    fun findAllAlta() = repository.findByAlta(true)

    /**
     * Método que cuanta el número de objetos de tipo Aeronave que poseen una detarminada matrícula
     * @param matrícula matrícula de la aeronave a contar
     * @return número de aeronaves que coincidan
     */
    fun countByMatricula(matricula : String) = repository.countByMatricula(matricula)

    override fun findById(id : UUID) = super.findById(id)

    override fun deleteById(id : UUID) = super.deleteById(id)

    fun editById(id : UUID) = super.save(super.findById(id).get())

    fun existById(id : UUID)= repository.existsById(id)

    /**
     * Método encargado de la creación del objeto de tipo Aeronave y su posterior guardado a partir del Dto proporcionado,
     * devolviendo otro con al información específica del objeto
     * @param nueva Dto con los datos del formulario de creación
     * @return Dto de la nueva aeronave creada
     */
    fun create (nueva: DtoAeronaveForm): DtoAeronaveSinFoto {
        if(this.countByMatricula(nueva.matricula.toUpperCase()) == 0){
            var aeronave = Aeronave(nueva.matricula.toUpperCase(), nueva.marca, nueva.modelo, nueva.motor, nueva.potencia,
                nueva.autonomia, nueva.velMax, nueva.velMin, nueva.velCru, false)
            this.save(aeronave)
            return aeronave.toGetDtoAeronaveSinFoto()
        }else{
            return throw NewAeronaveException(nueva.matricula.toUpperCase())
        }


    }

    /**
     * Método encargado de crear el objeto de tipo FotoAeronave, haciendo uso del servicio de FotoAeronaveServicio,
     * que a su vez usa los de imgur. Una vez creada, se relacionan entre si los objetos de tipo FotoAeronaveServicio y
     * Aeronave y se guardan en la base de datos. En caso de que ocurra algún problema, saltará una excepción
     * @param id Id de la aeronave a la que se le establece la foto
     * @param file Foto de la aeronave
     * @param servicioFoto Servicio de la entidad FotoAeronave
     * @return Dto de la aeronave actualizada
     */
    fun addFoto(id: UUID, file: MultipartFile, servicioFoto:FotoAeronaveServicio): DtoAeronaveResp {
        try {
            var foto = FotoAeronave()
            var guardado = servicioFoto.save( foto, file)
            var aeronave = this.findById(id).get()
            aeronave.addFoto(guardado)
            this.save(aeronave)
            servicioFoto.save(guardado)
            return aeronave.toGetDtoAeronaveResp()
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }
    }

    /**
     * Método encargado del borrado de la aeronave y la imagen asocida a esta, en el caso de que tenga una.
     * @param hash Cadena de confirmación de borrado
     * @param id id de la aeronave a la que se le va a borrar la foto
     * @param imgurStorageService Servicio de imgur
     * @return confirmación o no del exito de la operación
     */
    fun delete(hash: String, id: UUID, imgurStorageService: ImgurStorageService,
        registroService: RegistroService, servicioFoto: FotoAeronaveServicio): Boolean {
        var aeronave = this.findById(id).orElseThrow{ListaAeronaveNotFoundException(Aeronave::class.java)}
        if (registroService.countByAeronave(aeronave) == 0 ){
            if(aeronave.foto == null){
                this.delete(aeronave)
            }else{
                var foto = aeronave.foto!!
                if (aeronave.foto!!.deleteHash==hash){
                    imgurStorageService.delete(hash)
                    aeronave.deleteFoto()
                    servicioFoto.delete(foto)
                    this.delete(aeronave)
                }
            }
            return true
        }else{
            return false
        }
    }

    /**
     * Método encargado unicamente de la eliminación de la foto en imgur, sin eliminar la aeronave.
     * @param hash Cadena de confirmación de borrado
     * @param id id de la aeronave a la que se le va a borrar la foto
     * @param imgurStorageService Servicio de imgur
     * @return Unit
     */
    fun deleteFoto(hash: String, id: UUID, imgurStorageService: ImgurStorageService,
               registroService: RegistroService, servicioFoto: FotoAeronaveServicio): Unit {
        var aeronave = this.findById(id).orElseThrow{ListaAeronaveNotFoundException(Aeronave::class.java)}
        if(aeronave.foto != null){
            var foto = aeronave.foto!!
            if (aeronave.foto!!.deleteHash==hash){
                imgurStorageService.delete(hash)
                aeronave.deleteFoto()
                servicioFoto.delete(foto)
                this.edit(aeronave)
            }
        }

    }

    /**
     * Método encargado de listar todas las aeronaves, devolviendolas en una lista conformadas por Dto y
     * lanzando la excepción pertinente en el caso de no encontrar ninguna
     * @return listado de Dto de todas las aeronaves
     */
    fun listado(): List<DtoAeronaveResp> {
        return this.findAll().map{it.toGetDtoAeronaveResp()}
            .takeIf { it.isNotEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java)
    }

    /**
     * Método encargado de listar todas las aeronaves dadas de alta, devolviendolas en una lista conformadas por Dto y
     * lanzando la excepción pertinente en el caso de no encontrar ninguna
     * @return listado de Dto de las aeronaves dadas de alta
     */
    fun listadoAlta(): List<DtoAeronaveResp> {
        return this.findAllAlta()?.map{it.toGetDtoAeronaveResp()}
            .takeIf { !it.isNullOrEmpty() } ?: throw ListaAeronaveNotFoundException(Aeronave::class.java)
    }

    /**
     * Método encargado de buscar una aeronave por su ID, devolviendola en un Dto y
     * lanzando la excepción pertinente en el caso de no encontrarla
     * @param id Id de la aeronve
     * @return Dto de aeronave con el id
     */
    fun aeronaveId(id: UUID): DtoAeronaveResp? {
        return this.findById(id).map { it.toGetDtoAeronaveResp() }
            .orElseThrow { AeronaveSearchNotFoundException(id.toString()) }
    }

    /**
     * Método encargado de editar una aeronave , buscándola por ID, establecinedo los nuevos datos a partir del Dto correspondiente y
     * devolviendola como Dto. Se lanza la excepción pertinente en el caso de no encontrala
     * @param editada Dto con los datos del formulario
     * @param id Id de la aeronave
     * @return Dto de la aeronave modificada
     */
    fun editar(editada:DtoAeronaveForm, id: UUID): DtoAeronaveSinFoto? {
        return this.findById(id)
            .map { fromRepo ->
                fromRepo.matricula = editada.matricula
                fromRepo.marca = editada.marca
                fromRepo.modelo = editada.modelo
                fromRepo.motor = editada.motor
                fromRepo.potencia = editada.potencia
                fromRepo.autonomia = editada.autonomia
                fromRepo.velMax = editada.velMax
                fromRepo.velMin = editada.velMin
                fromRepo.velCru = editada.velCru
                this.save(fromRepo).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) }
    }

    /**
     * Método que busca una aeronave por su ID y cambia su estado, ya sea referente a si está dada de alta o baja o
     * si está en mantenimiento o no. Se lanza la excepción pertinente en el caso de no encontrala
     * @param id Id de la aeronave
     * @param opt Número que determina la propiedad a cambiar
     * @return Dto de la aeronave modificada
     */
    fun cambiarEstado(id: UUID, opt:Int): DtoAeronaveSinFoto? {
        return this.findById(id)
            .map { aero ->
                if(opt==1)
                    aero.mantenimiento = !aero.mantenimiento
                else
                    aero.alta = !aero.alta
                this.save(aero).toGetDtoAeronaveSinFoto()
            }.orElseThrow { AeronaveModifNotFoundException(id.toString()) }
    }

}