package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import java.time.LocalDateTime
import java.util.*
import javax.persistence.ManyToOne

data class DtoFacturaForm(

    var horaInicial : String,
    var horaFinal : String
)

data class DtoFacturaCliente(

    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: Producto,
    var tipo: Boolean
)

data class DtoFacturaAdmin(

    val id:UUID,
    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: DtoProductoEspecf,
    var comprador: Piloto,
    var tipo: Boolean
)

fun Factura.toGetDtoFacturaCliente():DtoFacturaCliente{
    return DtoFacturaCliente( precioTotal, fecha ,producto, producto.tipoLibre)

}

fun Factura.toGetDtoFacturaAdmin():DtoFacturaAdmin{
    return DtoFacturaAdmin( id!!, precioTotal, fecha ,producto.toGetDtoProductoEspecf(), comprador, producto.tipoLibre)

}