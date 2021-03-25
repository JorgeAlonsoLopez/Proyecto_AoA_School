package com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.retrofit.RegistroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistroHorasViewModel(application : Application) : AndroidViewModel(application) {

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: RegistroService
    var token:String = ""
    private var _registros = MutableLiveData<List<DtoRegistro>>()
    private val context = getApplication<Application>().applicationContext

    val registros: LiveData<List<DtoRegistro>>
        get() = _registros

    init {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        _registros.value = listOf()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(RegistroService::class.java)

        getRegistros()
    }

    private fun getRegistros() {
        service.listadoUsuario("Bearer "+token).enqueue(object: Callback<List<DtoRegistro>> {
            override fun onResponse(call: Call<List<DtoRegistro>>, response: Response<List<DtoRegistro>>) {
                if(response.code() == 200) {
                    _registros.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<DtoRegistro>>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }

    fun reload() {
        getRegistros()
    }
}