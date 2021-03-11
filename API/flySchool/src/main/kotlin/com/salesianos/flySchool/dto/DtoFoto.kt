package com.salesianos.flySchool.dto

import com.salesianos.flySchool.entity.FotoAeronave
import java.util.*

data class DTOFoto(
    var url : String,
    var deleteHash : String,
    val id: UUID
)


fun FotoAeronave.toGetDTOFoto():DTOFoto{
    return DTOFoto( dataId!!, deleteHash!! ,id!!)

}

data class DTOFotoUrl(
    var url : String
)


fun FotoAeronave.toGetDTOFotoUrl():DTOFotoUrl{
    return DTOFotoUrl(dataId!!)

}