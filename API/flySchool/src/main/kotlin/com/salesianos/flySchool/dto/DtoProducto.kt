package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Producto
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Clase que guarda la información proveniente del correspondinete formulario para la creación del objeto
 */
data class DtoProductoForm(

    @ApiModelProperty(value = "Tipo del producto, haciendo referencia a que tipo de piloto va dirigido")
    var tipoLibre : Boolean,

    @ApiModelProperty(value = "Nombre del producto")
    @get:NotBlank(message="{producto.nombre.blank}")
    var nombre : String,

    @ApiModelProperty(value = "Precio del producto")
    @get:Min(value=1,message="{producto.precio.min}")
    var precio : Double,

    @ApiModelProperty(value = "Horas de vuelo asociadas al producto")
    @get:Min(value=0,message="{producto.horasVuelo.min}")
    var horasVuelo : Int
)

/**
 *Clase que guarda la información del producto proveniente de la base de datos
 */
data class DtoProductoEspecf(

    @ApiModelProperty(value = "ID del producto")
    val id: UUID,

    @ApiModelProperty(value = "Tipo del producto, haciendo referencia a que tipo de piloto va dirigido")
    var tipoLibre : Boolean,

    @ApiModelProperty(value = "Nombre del producto")
    var nombre : String,

    @ApiModelProperty(value = "Precio del producto")
    var precio : Double,

    @ApiModelProperty(value = "Horas de vuelo asociadas al producto")
    var horasVuelo : Int,

    @ApiModelProperty(value = "Estado del producto del producto")
    var alta : Boolean
)

fun Producto.toGetDtoProductoEspecf():DtoProductoEspecf{
    return DtoProductoEspecf( id!!, tipoLibre ,nombre, precio, horasVuelo, alta)

}