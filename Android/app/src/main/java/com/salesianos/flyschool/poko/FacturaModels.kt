package com.salesianos.flyschool.poko

import java.time.LocalDateTime
import java.util.*


data class DtoFacturaCliente(

        var precioTotal : Double,
        var fecha : LocalDateTime,
        var producto: DtoProductoEspecf,
        var tipo: Boolean
)

data class DtoFacturaAdmin(

        val id: UUID,
        var precioTotal : Double,
        var fecha : LocalDateTime,
        var producto: DtoProductoEspecf,
        var comprador: DtoPilot,
        var tipo: Boolean
)