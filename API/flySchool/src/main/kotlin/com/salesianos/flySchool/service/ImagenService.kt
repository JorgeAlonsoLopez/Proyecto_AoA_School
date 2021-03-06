package com.salesianos.flySchool.service

import com.salesianos.flySchool.entity.FotoAeronave
import com.salesianos.flySchool.repository.FotoRepository
import com.salesianos.flySchool.upload.ImgurImageAttribute
import com.salesianos.flySchool.upload.ImgurStorageService
import org.springframework.stereotype.Service
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile


/**
 * Servicio perteneciente a la clase FotoAeronave
 * @see FotoAeronave
 */
@Service
class ImagenServicio(
    private val repo: FotoRepository
) {
    fun save(fotoAeronave: FotoAeronave) = repo.save(fotoAeronave)

    fun findAll() = repo.findAll()

    fun findById(id: UUID) = repo.findById(id)

    fun deleteById(id: UUID) = repo.deleteById(id)

    fun editById(id: UUID) = repo.save(findById(id).get())

}

/**
 * Servicio donde se implementan los métodos relacionados con el tratamiento de imagenes de imgur
 * @param imageStorageService Servicio de imgur
 */
@Service
class FotoAeronaveServicio(
    private val imageStorageService: ImgurStorageService
) : BaseService<FotoAeronave, UUID, FotoRepository>() {

    val logger: Logger = LoggerFactory.getLogger(ImagenServicio::class.java)

    /**
     * Método encargado de guardar la imagen en el servicio y establecer los valores del objeto de tipo FotoAeronave y guardarlo
     * @param e Entidad con los datos de la foto
     * @param file Imagen a guardar
     * @return entidad de la foto
     */
    fun save(e: FotoAeronave, file: MultipartFile) : FotoAeronave {
        var imageAttribute : Optional<ImgurImageAttribute> = Optional.empty()
        if (!file.isEmpty) {
            imageAttribute = imageStorageService.store(file)
        }
        if (imageAttribute!=null){
            e.dataI = imageStorageService.loadAsResource(imageAttribute.get().id!!).get().uri.toString()
            e.deleteHash=imageAttribute.get().deletehash!!

        }
        save(e)
        return e
    }

    /**
     * Elimia la imagen del servicio de almacenamiento y elimina el objeto de la base de datos
     * @param e entidad con los datos de la foto
     * @return Unit
     */
    override fun delete(e : FotoAeronave) {
        logger.debug("Eliminando la entidad $e")
        e?.let { it.deleteHash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(e)
    }



}