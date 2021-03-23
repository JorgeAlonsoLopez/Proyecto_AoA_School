package com.salesianos.flySchool.error

/**
 * Excepción EntityNotFoundException de la que van a heredar el resto de clases personalizadas
 */
open class EntityNotFoundException(val msg: String) : RuntimeException(msg)

/**
 * Excepción ocurrida cuando no se encuentra una entidad
 */
data class SingleEntityNotFoundException(
    val id: String,
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se puede encontrar la entidad de tipo ${javaClass.name} con ID: ${id}")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos de una misma clase
 */
data class ListEntityNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se pueden encontrar elementos del tipo ${javaClass.name} para esa consulta")

/**
 * Excepción ocurrida cuando no se encuentra un objeto de tipo Producto a la hora de modificarlo
 */
data class ProductoModifNotFoundException(
    val id: String
) : EntityNotFoundException("El producto a modificar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un objeto de tipo Producto a la hora de buscarla mediante su ID
 */
data class ProductoSearchNotFoundException(
    val id: String
) : EntityNotFoundException("El producto a buscar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos del tipo Producto
 */
data class ListaProductoNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("Para la operación que desea llevar a cabo, no se pudo encontrar ninguno de los productos en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un objeto de tipo Aeronave a la hora de modificarla
 */
data class AeronaveModifNotFoundException(
    val id: String
) : EntityNotFoundException("La aeronave a modificar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un objeto de tipo Aeronave a la hora de buscarla mediante su ID
 */
data class AeronaveSearchNotFoundException(
    val id: String
) : EntityNotFoundException("La aeronave a buscar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos del tipo Aeronave
 */
data class ListaAeronaveNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("Para la operación que desea llevar a cabo, no se pudo encontrar ninguna de las aeronaves en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos del tipo Factura
 */
data class ListaFacturasNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("Las facturas que desea buscar no se encuentran en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos del tipo RegistroVuelo
 */
data class ListaRegistroVueloNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se pudo encontrar ninguno de los registros de vuelos en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un objeto de tipo Usuario a la hora de buscarla mediante su ID
 */
data class UserSearchNotFoundException(
    val id: String
) : EntityNotFoundException("El usuario a buscar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un objetos del tipo Usuario a la hora de modificarlo
 */
data class UserModifNotFoundException(
    val id: String
) : EntityNotFoundException("El usuario que desea modificar con ID: ${id}, no se pudo encontrar en la base de datos")

/**
 * Excepción ocurrida cuando no se encuentra un conjunto de objetos del tipo Usuario
 */
data class ListaUsuariosNotFoundException(
    val javaClass: Class<out Any>
) : EntityNotFoundException("No se ha podido encontrar usuarios en la base de datos")

/**
 * Excepción ocurrida cuando se intenta registrar un usuario con un nombre de usuario que ya está siendo usado por otro
 */
data class NewUserException(
    val name: String
) : EntityNotFoundException("El usuario que intenta registrar con el nombre de usuario ${name} ya existe, inténtelo con otro")

