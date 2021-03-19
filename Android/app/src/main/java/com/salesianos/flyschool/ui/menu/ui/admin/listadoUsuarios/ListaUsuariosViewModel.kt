package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserInfo
import com.salesianos.flyschool.retrofit.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaUsuariosViewModel(application : Application) : AndroidViewModel(application){

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: UsuarioService
    var token:String = ""
    private var _usuarios = MutableLiveData<List<DtoUserInfo>>()
    private val context = getApplication<Application>().applicationContext

    val usuarios: LiveData<List<DtoUserInfo>>
        get() = _usuarios

    init {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        _usuarios.value = listOf()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(UsuarioService::class.java)

        getUsuarios()
    }

    private fun getUsuarios() {
        service.getListaUsuarios("Bearer "+token).enqueue(object: Callback<List<DtoUserInfo>> {
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

    fun reloadUsuarios() {
        getUsuarios()
    }


}