package com.salesianos.flyschool.ui.detalle.admin.registroProducto

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.*
import com.salesianos.flyschool.retrofit.AeronaveService
import com.salesianos.flyschool.retrofit.ProductoService
import com.salesianos.flyschool.ui.detalle.admin.registroAeronave.RegistroAeronaveActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class RegistroProductoActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: ProductoService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    var edit : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_producto)

        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ctx = this
        service = retrofit.create(ProductoService::class.java)

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        if(intent.extras?.getString("id") != "N"){
            id = UUID.fromString(intent.extras?.getString("id"))
        }
        edit = intent.extras?.getBoolean("edit")!!


        var ensenyanza : RadioButton = findViewById(R.id.radio_new_product_ensen)
        var libre : RadioButton = findViewById(R.id.radio_new_product_libre)

        var nombre : EditText = findViewById(R.id.input_new_product_nombre)
        var horas : EditText = findViewById(R.id.input_new_product_horas)
        var precio : EditText = findViewById(R.id.input_new_product_precio)


        var btnSend : Button = findViewById(R.id.btn_confirm_new_prodc)
        var btnCancel : Button = findViewById(R.id.btn_cancel_new_prodc)

        if(edit){
            service.productoId("Bearer " + token, id).enqueue(object :
                Callback<DtoProductoEspecf> {
                override fun onResponse(call: Call<DtoProductoEspecf>, response: Response<DtoProductoEspecf>
                ) {
                    if (response.code() == 200) {
                        nombre.setText(response.body()?.nombre)
                        precio.setText(response.body()?.precio.toString())
                        horas.setText(response.body()?.horasVuelo.toString())
                        if(response.body()?.tipoLibre!!){
                            libre.isChecked = true
                        }else{
                            ensenyanza.isChecked = true
                        }
                    }
                }
                override fun onFailure(call: Call<DtoProductoEspecf>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })
        }


        btnSend.setOnClickListener(View.OnClickListener {

            if(nombre.text.isNotBlank() && precio.text.isNotBlank() && horas.text.isNotBlank()){
                var form: DtoProductoForm
                if(ensenyanza.isChecked){
                    form = DtoProductoForm(false, nombre.text.toString(), precio.text.toString().toDouble(),
                        horas.text.toString().toInt())
                }else{
                    form = DtoProductoForm(true, nombre.text.toString(), precio.text.toString().toDouble(),
                        horas.text.toString().toInt())
                }

                if(!edit){
                    service.crear("Bearer " + token, form).enqueue(object :
                        Callback<DtoProductoEspecf> {
                        override fun onResponse(call: Call<DtoProductoEspecf>, response: Response<DtoProductoEspecf>) {
                            if (response.code() == 201) {
                                (ctx as RegistroProductoActivity).finish()
                            }
                        }

                        override fun onFailure(call: Call<DtoProductoEspecf>, t: Throwable) {
                            Log.i("Error", "Error")
                            Log.d("Error", t.message!!)
                        }
                    })
                }else{
                    service.editar("Bearer " + token, form, id).enqueue(object :
                        Callback<DtoProductoEspecf> {
                        override fun onResponse(call: Call<DtoProductoEspecf>, response: Response<DtoProductoEspecf>) {
                            if (response.code() == 200) {
                                (ctx as RegistroProductoActivity).finish()
                            }
                        }
                        override fun onFailure(call: Call<DtoProductoEspecf>, t: Throwable) {
                            Log.i("Error", "Error")
                            Log.d("Error", t.message!!)
                        }
                    })
                }
            }

        })

        btnCancel.setOnClickListener(View.OnClickListener { (ctx as RegistroProductoActivity).finish() })


    }
}