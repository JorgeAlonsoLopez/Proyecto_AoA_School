package com.salesianos.flyschool.ui.detalle.admin.detalleProducto

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.ProductoService
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.detallePiloto.DetallePilotoActivity
import com.salesianos.flyschool.ui.detalle.admin.editarUsuario.EditarUsuarioActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetalleProductoActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: ProductoService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    var altaPilot : Boolean = true
    lateinit var nombre : TextView
    lateinit var tipo : TextView
    lateinit var precio : TextView
    lateinit var horas : TextView
    lateinit var estado : TextView
    lateinit var eliminar : Button
    lateinit var alta_baja : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ctx = this
        service = retrofit.create(ProductoService::class.java)

        id = UUID.fromString(intent.extras?.getString("id"))

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        nombre = findViewById(R.id.text_detalle_producto_nombre)
        tipo = findViewById(R.id.text_detalle_producto_tipo)
        precio = findViewById(R.id.text_detalle_producto_precio)
        horas = findViewById(R.id.text_detalle_producto_horas)
        estado = findViewById(R.id.text_detalle_producto_estado)

        eliminar = findViewById(R.id.btn_detalle_producto_eliminar)
        alta_baja = findViewById(R.id.btn_detalle_producto_alta)

        cargarDatos()

        eliminar.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.delete_prodc))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.eliminar)) { view, _ ->
                        service.eliminar("Bearer "+token, id).enqueue(object : Callback<Unit> {
                            override fun onResponse(call: Call<Unit>, response: Response<Unit>
                            ) {
                                if (response.code() == 204) {
                                    (ctx as DetalleProductoActivity).finish()
                                }else if (response.code() == 400){
                                    Toast.makeText(applicationContext, getString(R.string.delete_product), Toast.LENGTH_LONG).show()
                                }
                            }
                            override fun onFailure(call: Call<Unit>, t: Throwable) {
                                Log.i("Error", "Error")
                                Log.d("Error", t.message!!)
                            }
                        })
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

            dialog.show()
        })

        alta_baja.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.cambiar_estado_prod))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.cambiar)) { view, _ ->
                        service.cambiarEstado("Bearer "+token, id).enqueue(object : Callback<DtoProductoEspecf> {
                            override fun onResponse(call: Call<DtoProductoEspecf>, response: Response<DtoProductoEspecf>
                            ) {
                                if (response.code() == 200) {
                                    (ctx as DetalleProductoActivity).recreate()
                                }
                            }
                            override fun onFailure(call: Call<DtoProductoEspecf>, t: Throwable) {
                                Log.i("Error", "Error")
                                Log.d("Error", t.message!!)
                            }
                        })
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

            dialog.show()
        })


    }

    fun cargarDatos(){
        service.productoId("Bearer "+token, id).enqueue(object : Callback<DtoProductoEspecf> {
            override fun onResponse(call: Call<DtoProductoEspecf>, response: Response<DtoProductoEspecf>
            ) {
                if (response.code() == 200) {
                    nombre.text = response.body()?.nombre
                    precio.text = response.body()?.precio.toString()
                    horas.text = response.body()?.horasVuelo.toString()
                    if(response.body()?.tipoLibre!!) tipo.text = getString(R.string.text_vuelo_libre) else tipo.text = getString(R.string.text_instrucc)
                    if(response.body()?.alta!!) estado.text = getString(R.string.dado_de_alta) else estado.text = getString(R.string.dado_de_baja)
                    if(response.body()?.alta!!) alta_baja.text = getString(R.string.dar_de_baja) else alta_baja.text = getString(R.string.dar_de_alta)
                }
            }
            override fun onFailure(call: Call<DtoProductoEspecf>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }

}