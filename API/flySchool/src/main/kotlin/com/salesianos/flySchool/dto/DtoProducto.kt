package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Producto
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class DtoProductoForm(
    var tipoLibre : Boolean,
    @get:NotBlank(message="{producto.nombre.blank}")
    var nombre : String,
    @get:Min(value=1,message="{producto.precio.min}")
    var precio : Double,
    @get:Min(value=0,message="{producto.horasVuelo.min}")
    var horasVuelo : Int
)

data class DtoProductoEspecf(
    val id: UUID,
    var tipoLibre : Boolean,
    var nombre : String,
    var precio : Double,
    var horasVuelo : Int,
    var alta : Boolean
)

fun Producto.toGetDtoProductoEspecf():DtoProductoEspecf{
    return DtoProductoEspecf( id!!, tipoLibre ,nombre, precio, horasVuelo, alta)

}