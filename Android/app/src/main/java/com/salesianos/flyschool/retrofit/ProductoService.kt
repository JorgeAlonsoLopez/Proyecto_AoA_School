package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.poko.DtoProductoForm
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ProductoService {

    @POST("producto/")
    fun crear(@Header("Authorization") token: String, @Body nueva: DtoProductoForm) : Call<DtoProductoEspecf>

    @PUT("producto/{id}")
    fun editar(@Header("Authorization") token: String, @Body editada: DtoProductoForm, @Path("id") id : UUID): Call<DtoProductoEspecf>

    @PUT("producto/{id}/est")
    fun cambiarEstado(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoProductoEspecf>

    @DELETE("producto/{id}")
    fun eliminar(@Header("Authorization") token: String, @Path("id") id : UUID): Call<Unit>

    @GET("producto/")
    fun listado(@Header("Authorization") token: String) : Call<List<DtoProductoEspecf>>

    @GET("producto/alta/{licencia}")
    fun listadoAlta(@Header("Authorization") token: String, @Path("licencia") licencia: Boolean) : Call<List<DtoProductoEspecf>>

    @GET("producto/{id}")
    fun productoId(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoProductoEspecf>


}