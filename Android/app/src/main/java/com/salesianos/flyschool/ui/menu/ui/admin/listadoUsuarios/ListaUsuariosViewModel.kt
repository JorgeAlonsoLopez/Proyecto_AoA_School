package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserInfo
import com.salesianos.flyschool.retrofit.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaUsuariosViewModel : ViewModel(){

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: UsuarioService
    var token:String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkMzU1NzVkZC1jYWQ4LTQ5MmYtYjk3Mi1iMGExYmZjMTFjMDYiLCJleHAiOjE2MTYzMjU3MjEsImlhdCI6MTYxNjA2NjUyMSwicmVmcmVzaCI6ZmFsc2UsImZ1bGxuYW1lIjoiQUxiZXJ0byBHb256YWxleiIsInJvbGVzIjoiQURNSU4ifQ.-E3gikNV0RkRd_g1Y7aZjZ-20KD64bTmxOIPHv8RjosFDrB1Q_ecqrg8FyfaqCuYenUuP4rtZYTH5Yr5U2smKg"
    private var _usuarios = MutableLiveData<List<DtoUserInfo>>()

    val usuarios: LiveData<List<DtoUserInfo>>
        get() = _usuarios

    init {

        _usuarios.value = listOf()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(UsuarioService::class.java)

        getUsuarios()
    }

    private fun getUsuarios() {
        service.getLstaUsuarios("Bearer "+token).enqueue(object: Callback<List<DtoUserInfo>> {
            override fun onResponse(call: Call<List<DtoUserInfo>>, response: Response<List<DtoUserInfo>>) {
                if(response.code() == 200) {
                    _usuarios.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<DtoUserInfo>>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }

    fun setTok(tok:String){
        token = tok
    }


}