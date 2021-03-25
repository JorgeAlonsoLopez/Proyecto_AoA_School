package com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.retrofit.AeronaveService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaAeronavesViewModel(application : Application) : AndroidViewModel(application) {

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: AeronaveService
    var token:String = ""
    private var _aeronaves = MutableLiveData<List<DtoAeronaveResp>>()
    private val context = getApplication<Application>().applicationContext

    val aeronaves: LiveData<List<DtoAeronaveResp>>
        get() = _aeronaves

    init {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        _aeronaves.value = listOf()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(AeronaveService::class.java)

        getAeronaves()
    }

    private fun getAeronaves() {
        service.listado("Bearer "+token).enqueue(object: Callback<List<DtoAeronaveResp>> {
            override fun onResponse(call: Call<List<DtoAeronaveResp>>, response: Response<List<DtoAeronaveResp>>) {
                if(response.code() == 200) {
                    _aeronaves.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<DtoAeronaveResp>>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }

    fun reloadAeronaves() {
        getAeronaves()
    }
}