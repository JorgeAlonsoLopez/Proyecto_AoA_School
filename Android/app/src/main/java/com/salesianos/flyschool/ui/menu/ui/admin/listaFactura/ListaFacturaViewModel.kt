package com.salesianos.flyschool.ui.menu.ui.admin.listaFactura

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.retrofit.FacturaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaFacturaViewModel(application : Application) : AndroidViewModel(application) {

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: FacturaService
    var token:String = ""
    private var _facturas = MutableLiveData<List<DtoFacturaAdmin>>()
    private val context = getApplication<Application>().applicationContext

    val facturas: LiveData<List<DtoFacturaAdmin>>
        get() = _facturas

    init {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        _facturas.value = listOf()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(FacturaService::class.java)

        getFacturas()
    }

    private fun getFacturas() {
        service.listado("Bearer "+token).enqueue(object: Callback<List<DtoFacturaAdmin>> {
            override fun onResponse(call: Call<List<DtoFacturaAdmin>>, response: Response<List<DtoFacturaAdmin>>) {
                if(response.code() == 200) {
                    _facturas.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<DtoFacturaAdmin>>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }


}