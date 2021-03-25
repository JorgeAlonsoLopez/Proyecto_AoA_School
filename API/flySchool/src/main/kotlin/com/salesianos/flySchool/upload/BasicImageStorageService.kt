package com.salesianos.flySchool.upload

import org.springframework.web.multipart.MultipartFile
import java.util.*
import org.springframework.core.io.Resource
import java.nio.file.Path

/**
 * Interface que aporta los métodos de guardado, carga y borrado re recursos en imgur
 */
interface BasicImageStorageService<T, ID, DID> {

    fun store(file : MultipartFile) : Optional<T>

    fun loadAsResource(id : ID) : Optional<MediaTypeUrlResource>

    fun delete(id : DID)


}