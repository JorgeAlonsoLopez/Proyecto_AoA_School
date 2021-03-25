package com.salesianos.flySchool.upload

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.*

interface ImgurStorageService : BasicImageStorageService<ImgurImageAttribute, String, String>

/**
 * Clase encargada del almacenamiento, cargado y borrado de imagenes en imgur, haciendo uso de la interfaz BasicImageStorageService
 */
@Service
class ImgurStorageServiceImpl(
    private val imgurService: ImgurService
)
    : ImgurStorageService {

    val logger: Logger = LoggerFactory.getLogger(ImgurStorageService::class.java)

    /**
     * Método que devuelve un Optional vacío o con el id y el hash de la imagen si se ha subido de forma correcta
     */
    override fun store(file: MultipartFile) : Optional<ImgurImageAttribute> {

        if (!file.isEmpty) {
            var imgReq =
                NewImageReq(Base64.getEncoder().encodeToString(file.bytes),
                    file.originalFilename.toString())
            var imgRes = imgurService.upload(imgReq)
            if(imgRes.isPresent)
                return Optional.of(ImgurImageAttribute(imgRes.get().data.id, imgRes.get().data.deletehash))
        }

        return Optional.empty()

    }

    /**
     * Método que obtiene un Optional con la url de la imagen, a partir del id de la misma, o uno vacío en el caso de error
     */
    override fun loadAsResource(id: String) : Optional<MediaTypeUrlResource> {
        var response = imgurService.get(id)
        if (response.isPresent) {
            var resource = MediaTypeUrlResource(response.get().data.type, URI.create(response.get().data.link))
            if (resource.exists() || resource.isReadable)
                return Optional.of(resource)
        }

        return Optional.empty()

    }

    /**
     * Método de borrado de la imagen a partir del hash obtenido en el método de guardado
     */
    override fun delete(deletehash: String) : Unit {
        logger.debug("Eliminando la imagen $deletehash")
        imgurService.delete(deletehash)
    }


}

/**
 * Clase que devuelve una url a partir de una uri y un string
 */
class MediaTypeUrlResource(
    val mediaType: String, var uri: URI) : UrlResource(uri)