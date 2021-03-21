package com.salesianos.flyschool.ui.menu.ui.piloto.compras

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.FacturaService
import com.salesianos.flyschool.retrofit.ProductoService
import com.salesianos.flyschool.retrofit.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ComprasViewModel(application : Application) : AndroidViewModel(application) {

    val baseUrl = "https://aoa-school.herokuapp.com/"
    var retrofit: Retrofit
    var service: ProductoService
    var facturaService : FacturaService
    var serviceUser: UsuarioService
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
        serviceUser = retrofit.create(UsuarioService::class.java)
        facturaService= retrofit.create(FacturaService::class.java)

        getProductos()
    }

    private fun getProductos() {

        var licencia : Boolean = false

        serviceUser.me("Bearer "+token).enqueue(object : Callback<DtoUserInfoSpeci> {
            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
            ) {
                if (response.code() == 200) {
                    if(response.body()?.rol == "PILOT"){
                        val id = UUID.fromString(response.body()?.id)
                        serviceUser.detallePiloto("Bearer "+token, id).enqueue(object : Callback<DtoPilot> {
                            override fun onResponse(call: Call<DtoPilot>, response: Response<DtoPilot>
                            ) {
                                if (response.code() == 200) {
                                    licencia = response.body()?.licencia!!
                                    service.listadoAlta("Bearer "+token, licencia).enqueue(object: Callback<List<DtoProductoEspecf>> {
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
                            }
                            override fun onFailure(call: Call<DtoPilot>, t: Throwable) {
                                Log.i("Error", "Error")
                                Log.d("Error", t.message!!)
                            }
                        })
                    }
                }
            }
            override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })
    }

    fun comprar(id:String){
        facturaService.crear("Bearer "+token, UUID.fromString(id)).enqueue(object: Callback<DtoFacturaAdmin> {
            override fun onResponse(call: Call<DtoFacturaAdmin>, response: Response<DtoFacturaAdmin>) {
                if(response.code() == 201) {
                    Toast.makeText(context, "La operación de compra se ha procesado correctamente", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "La operación no se ha podido llevar a cabo", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DtoFacturaAdmin>, t: Throwable) {
                Log.i("Error","ha entrado en onFailure")
                Log.d("Error",t.message!!)
            }
        })
    }

}