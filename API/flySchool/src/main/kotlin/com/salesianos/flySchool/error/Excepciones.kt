package com.salesianos.flySchool.error

open class EntityNotFoundException(val msg: String) : RuntimeException(msg)

data class SingleEntityNotFoundException(
    val id: String,
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se puede encontrar la entidad de tipo ${javaClass.name} con ID: ${id}")

data class ListEntityNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se pueden encontrar elementos del tipo ${javaClass.name} para esa consulta")

data class ProductoModifNotFoundException(
    val id: String
) : EntityNotFoundException("El producto a modificar con ID: ${id}, no se pudo encontrar en la base de datos")

data class ProductoSearchNotFoundException(
    val id: String
) : EntityNotFoundException("El producto a buscar con ID: ${id}, no se pudo encontrar en la base de datos")

data class ListaProductoNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("Para la operación que desa llevar a cabo, no se pudo encontrar ninguno de los productos en la base de datos")

data class AeronaveModifNotFoundException(
    val id: String
) : EntityNotFoundException("La aeronave a modificar con ID: ${id}, no se pudo encontrar en la base de datos")

data class AeronaveSearchNotFoundException(
    val id: String
) : EntityNotFoundException("La aeronave a buscar con ID: ${id}, no se pudo encontrar en la base de datos")

data class ListaAeronaveNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("Para la operación que desa llevar a cabo, no se pudo encontrar ninguna de las aeronaves en la base de datos")