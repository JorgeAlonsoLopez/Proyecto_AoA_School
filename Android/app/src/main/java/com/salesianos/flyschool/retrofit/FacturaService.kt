package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoFacturaCliente
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface FacturaService {

    @GET("factura/")
    fun listado(@Header("Authorization") token: String) : Call<List<DtoFacturaAdmin>>

    @GET("factura/user")
    fun listadoUsuario(@Header("Authorization") token: String) : Call<List<DtoFacturaCliente>>

    @POST("factura/{id}")
    fun crear(@Header("Authorization") token: String, @Path("id") id : UUID) : Call<DtoFacturaAdmin>


}