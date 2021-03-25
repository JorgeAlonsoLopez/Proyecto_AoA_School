package com.salesianos.flyschool.retrofit

import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.poko.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface WeatherService {


    @GET("weather?id=2510911&units=metric&lang=es&appid=fc5b83df539792fb051c1fad83659fe2")
    fun tiempo(): Call<WeatherResponse>

}