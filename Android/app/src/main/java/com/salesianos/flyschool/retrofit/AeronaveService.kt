package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface AeronaveService {

    @POST("aeronave/")
    fun crear(@Header("Authorization") token: String, @Body dto: DtoAeronaveForm): Call<DtoAeronaveSinFoto>

    @Multipart
    @POST("aeronave/{id}")
    fun addFoto(@Header("Authorization") token: String, @Part file: MultipartBody.Part,
                @Path("id") id : UUID): Call<DtoAeronaveResp>

    @DELETE("aeronave/{id}/{hash}")
    fun deletePhoto(@Header("Authorization") token: String, @Path("id") id : UUID,
                    @Path("hash") hash : String): Call<Any>


    @GET("aeronave/")
    fun listado(@Header("Authorization") token: String): Call<List<DtoAeronaveResp>>

    @GET("aeronave/alta")
    fun listadoAlta(@Header("Authorization") token: String): Call<List<DtoAeronaveResp>>

    @GET("aeronave/{id}")
    fun aeronaveId(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoAeronaveResp>

    @PUT("aeronave/{id}/")
    fun editar(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoAeronaveSinFoto>

    @PUT("aeronave/{id}/{opt}")
    fun estado(@Header("Authorization") token: String, @Path("id") id : UUID,
               @Path("opt") opt : Int): Call<DtoAeronaveSinFoto>




}