package com.salesianos.flyschool.ui.detalle.admin.detalleAeronave

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.load
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoAeronaveSinFoto
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.AeronaveService
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.detallePiloto.DetallePilotoActivity
import com.salesianos.flyschool.ui.detalle.admin.editarUsuario.EditarUsuarioActivity
import com.salesianos.flyschool.ui.detalle.admin.registroAeronave.RegistroAeronaveActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetalleAeronaveActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: AeronaveService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    lateinit var hash : String
    lateinit var foto : ImageView
    lateinit var matricula : TextView
    lateinit var modelo : TextView
    lateinit var marca : TextView
    lateinit var motor : TextView
    lateinit var potencia : TextView
    lateinit var alcance : TextView
    lateinit var max : TextView
    lateinit var crucero : TextView
    lateinit var min : TextView
    lateinit var estado : TextView
    lateinit var operatividad : TextView
    lateinit var btnAlta : Button
    lateinit var btnMantenimiento : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_aeronave)
        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ctx = this
        service = retrofit.create(AeronaveService::class.java)

        id = UUID.fromString(intent.extras?.getString("id"))

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }


        matricula = findViewById(R.id.text_matricula_detalle_aeronave_admin)
        modelo = findViewById(R.id.text_modelo_detalle_aeronave_admin)
        marca = findViewById(R.id.text_marca_detalle_aeronave_admin)
        motor = findViewById(R.id.text_motor_detalle_aeronave_admin)
        potencia = findViewById(R.id.text_potencia_detalle_aeronave_admin)
        alcance = findViewById(R.id.text_autonomia_detalle_aeronave_admin)
        max = findViewById(R.id.text_maxima_detalle_aeronave_admin)
        min = findViewById(R.id.text_minima_detalle_aeronave_admin)
        crucero = findViewById(R.id.text_crucero_detalle_aeronave_admin)
        estado = findViewById(R.id.text_estado_detalle_aeronave_admin)
        operatividad = findViewById(R.id.text_operatividad_detalle_aeronave_admin)
        foto = findViewById(R.id.img_foto_detalle_aeronave_admin)

        var btnEditar : Button = findViewById(R.id.btn_redirect_form_aeronave)
        var btnEliminar : Button = findViewById(R.id.btn_eliminar_aeronave)
        btnAlta = findViewById(R.id.btn_estado_aeronave)
        btnMantenimiento = findViewById(R.id.btn_operativo_aeronave)

        cargarDatos()

        btnEditar.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, RegistroAeronaveActivity::class.java).apply {
                putExtra("id", id.toString())
                putExtra("edit", true)
            }
            ctx.startActivity(intent)
        })

        btnEliminar.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.delete_aero))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.eliminar)) { view, _ ->
                        service.delete("Bearer "+token, id, hash).enqueue(object : Callback<Any> {
                            override fun onResponse(call: Call<Any>, response: Response<Any>
                            ) {
                                if (response.code() == 204) {
                                    (ctx as DetalleAeronaveActivity).finish()
                                }else if (response.code() == 400){
                                    Toast.makeText(applicationContext, getString(R.string.delete_aeronv), Toast.LENGTH_LONG).show()
                                }
                            }
                            override fun onFailure(call: Call<Any>, t: Throwable) {
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

        btnAlta.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.cambiar_estado_aero))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.cambiar)) { view, _ ->
                        service.estado("Bearer "+token, id,0).enqueue(object : Callback<DtoAeronaveSinFoto> {
                            override fun onResponse(call: Call<DtoAeronaveSinFoto>, response: Response<DtoAeronaveSinFoto>
                            ) {
                                if (response.code() == 200) {
                                    (ctx as DetalleAeronaveActivity).recreate()
                                }
                            }
                            override fun onFailure(call: Call<DtoAeronaveSinFoto>, t: Throwable) {
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

        btnMantenimiento.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.mantenim_aero))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.cambiar)) { view, _ ->
                        service.estado("Bearer "+token, id,1).enqueue(object : Callback<DtoAeronaveSinFoto> {
                            override fun onResponse(call: Call<DtoAeronaveSinFoto>, response: Response<DtoAeronaveSinFoto>
                            ) {
                                if (response.code() == 200) {
                                    (ctx as DetalleAeronaveActivity).recreate()
                                }
                            }
                            override fun onFailure(call: Call<DtoAeronaveSinFoto>, t: Throwable) {
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
        service.aeronaveId("Bearer "+token, id).enqueue(object : Callback<DtoAeronaveResp> {
            override fun onResponse(call: Call<DtoAeronaveResp>, response: Response<DtoAeronaveResp>
            ) {
                if (response.code() == 200) {
                    matricula.text = response.body()?.matricula
                    modelo.text = response.body()?.modelo
                    marca.text = response.body()?.marca
                    motor.text = response.body()?.motor
                    potencia.text = response.body()?.potencia.toString()
                    alcance.text = response.body()?.autonomia.toString()
                    max.text = response.body()?.velMax.toString()
                    min.text = response.body()?.velMin.toString()
                    crucero.text = response.body()?.velCru.toString()
                    foto.load(response.body()?.fotoURL!!.url)
                    hash= response.body()?.fotoURL!!.hash
                    if(response.body()?.mantenimiento!!){
                        operatividad.text = getString(R.string.manten)
                        btnMantenimiento.text = getString(R.string.finaliz_manten)
                    }else{
                        operatividad.text = getString(R.string.operativo)
                        btnMantenimiento.text = getString(R.string.llevar_a_manten)
                    }
                    if(response.body()?.alta!!){
                        estado.text = getString(R.string.dado_de_alta)
                        btnAlta.text = getString(R.string.dar_de_baja)
                    }else{
                        estado.text = getString(R.string.dado_de_baja)
                        btnAlta.text = getString(R.string.dar_de_alta)
                    }
                }
            }
            override fun onFailure(call: Call<DtoAeronaveResp>, t: Throwable) {
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