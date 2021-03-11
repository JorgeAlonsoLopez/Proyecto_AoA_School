package com.salesianostriana.flySchool.dto

import com.salesianostriana.flySchool.entity.Producto
import java.util.*

data class DtoProductoForm(
    var tipoLibre : Boolean,
    var nombre : String,
    var precio : Double,
    var horasVuelo : Int
)

data class DtoProductoEspecf(
    val id: UUID,
    var tipoLibre : Boolean,
    var nombre : String,
    var precio : Double,
    var horasVuelo : Int
)

fun Producto.toGetDtoProductoEspecf():DtoProductoEspecf{
    return DtoProductoEspecf( id!!, tipoLibre ,nombre, precio, horasVuelo)

}