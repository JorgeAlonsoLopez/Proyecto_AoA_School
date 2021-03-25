package com.salesianos.flyschool.ui.menu.ui.admin.listaProductos

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.retrofit.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaProductosViewModel(application : Application) : AndroidViewModel(application) {

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: ProductoService
    var token:String = ""
    private var _productos = MutableLiveData<List<DtoProductoEspecf>>()
    private val context = getApplication<Application>().applicationContext

    val productos: LiveData<List<DtoProductoEspecf>>
        get() = _productos

    init {

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        _productos.value = listOf()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ProductoService::class.java)

        getProductos()
    }

    private fun getProductos() {
        service.listado("Bearer "+token).enqueue(object: Callback<List<DtoProductoEspecf>> {
            override fun onResponse(call: Call<List<DtoProductoEspecf>>, response: Response<List<DtoProductoEspecf>>) {
                if(response.code() == 200) {
                    _productos.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<DtoProductoEspecf>>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }

    fun reload() {
        getProductos()
    }


}