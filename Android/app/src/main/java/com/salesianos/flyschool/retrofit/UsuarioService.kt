package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface UsuarioService {

    @POST("auth/login")
    fun login(@Body dto: DtoLogin): Call<LoginResponse>

    @GET("usuario/me")
    fun me(@Header("Authorization") token: String): Call<DtoUserInfoSpeci>

    @GET("usuario/")
    fun getListaUsuarios(@Header("Authorization") token: String): Call<List<DtoUserInfo>>

    @GET("usuario/{id}")
    fun detalle(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoUserInfoSpeci>

    @GET("usuario/piloto/{id}")
    fun detallePiloto(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoPilot>

    @PUT("usuario/licencia/{id}")
    fun habilitarLicencia(@Header("Authorization") token: String, @Path("id") id : UUID): Call<Any>

    @PUT("usuario/{id}/est")
    fun estado(@Header("Authorization") token: String, @Path("id") id : UUID): Call<DtoUserInfoSpeci>


}