package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.DtoLogin
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.poko.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UsuarioService {

    @POST("auth/login")
    fun login(@Body dto: DtoLogin): Call<LoginResponse>

    @GET("usuario/me")
    fun me(@Header("Authorization") token: String): Call<DtoUserInfoSpeci>



}