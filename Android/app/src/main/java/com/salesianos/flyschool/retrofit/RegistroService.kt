package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.poko.DtoRegistroForm
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RegistroService {

    @GET("registro/")
    fun listado(@Header("Authorization") token: String) : Call<List<DtoRegistro>>

    @GET("registro/user")
    fun listadoUsuario(@Header("Authorization") token: String) : Call<List<DtoRegistro>>

    @POST("registro/{id}")
    fun crear(@Header("Authorization") token: String,  @Body nueva: DtoRegistroForm, @Path("id") id : UUID) : Call<DtoRegistro>


}