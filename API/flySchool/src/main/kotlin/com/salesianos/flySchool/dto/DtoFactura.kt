package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime
import java.util.*

/**
 * Clase que guarda la información de la factura que se le va a mostar al cliente
 */
data class DtoFacturaCliente(

    @ApiModelProperty(value = "Precio de la compra del producto")
    var precioTotal : Double,

    @ApiModelProperty(value = "Fecha de la operacón de compra")
    var fecha : LocalDateTime,

    @ApiModelProperty(value = "Información del producto comprado")
    var producto: Producto,

    @ApiModelProperty(value = "Tipo del producto comprado, si el paquete de horas es de enseñanza(false) o vuelo libre(true)")
    var tipo: Boolean
)

/**
 * Clase que guarda la información de la factura que se le va a mostar al administrador
 */
data class DtoFacturaAdmin(

    @ApiModelProperty(value = "ID de la factura")
    val id:UUID,

    @ApiModelProperty(value = "Precio de la compra del producto")
    var precioTotal : Double,

    @ApiModelProperty(value = "Fecha de la operacón de compra")
    var fecha : LocalDateTime,

    @ApiModelProperty(value = "Información del producto comprado")
    var producto: DtoProductoEspecf,

    @ApiModelProperty(value = "Información parcial del piloto que ha ralizado la compra")
    var comprador: DtoPilot,

    @ApiModelProperty(value = "Tipo del producto comprado, si el paquete de horas es de enseñanza(false) o vuelo libre(true)")
    var tipo: Boolean
)

fun Factura.toGetDtoFacturaCliente():DtoFacturaCliente{
    return DtoFacturaCliente( precioTotal, fecha ,producto, producto.tipoLibre)
}

fun Factura.toGetDtoFacturaAdmin():DtoFacturaAdmin{
    return DtoFacturaAdmin( id!!, precioTotal, fecha ,producto.toGetDtoProductoEspecf(), comprador.toGetDtoUserInfoSpeciPilot(), producto.tipoLibre)
}