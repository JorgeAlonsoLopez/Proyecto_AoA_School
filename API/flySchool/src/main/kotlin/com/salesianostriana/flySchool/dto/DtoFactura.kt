package com.salesianostriana.flySchool.dto

import com.salesianostriana.flySchool.entity.Factura
import com.salesianostriana.flySchool.entity.Piloto
import com.salesianostriana.flySchool.entity.Producto
import java.time.LocalDateTime
import java.util.*
import javax.persistence.ManyToOne

data class DtoFacturaCliente(

    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: Producto,
)

data class DtoFacturaAdmin(

    val id:UUID,
    var precioTotal : Double,
    var fecha : LocalDateTime,
    var producto: DtoProductoEspecf,
    var comprador: Piloto
)

fun Factura.toGetDtoFacturaCliente():DtoFacturaCliente{
    return DtoFacturaCliente( precioTotal, fecha ,producto)

}

fun Factura.toGetDtoFacturaAdmin():DtoFacturaAdmin{
    return DtoFacturaAdmin( id!!, precioTotal, fecha ,producto.toGetDtoProductoEspecf(), comprador)

}