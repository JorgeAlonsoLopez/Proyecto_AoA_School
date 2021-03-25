package com.salesianos.flyschool.ui.detalle.admin.detallePiloto

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.editarUsuario.EditarUsuarioActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetallePilotoActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    var licenciaPilot : Boolean = true
    var altaPilot : Boolean = true
    lateinit var nombre : TextView
    lateinit var user : TextView
    lateinit var fecha : TextView
    lateinit var telef : TextView
    lateinit var email : TextView
    lateinit var licenc : TextView
    lateinit var tarj : TextView
    lateinit var btnEditar : Button
    lateinit var alta_baja : Button
    lateinit var licencia : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_piloto)
        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        ctx = this
        service = retrofit.create(UsuarioService::class.java)

        id = UUID.fromString(intent.extras?.getString("id"))

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        nombre = findViewById(R.id.text_detalle_admin_piloto_nombre)
        user = findViewById(R.id.text_detalle_admin_piloto_nombreUsuario)
        fecha = findViewById(R.id.text_detalle_admin_piloto_fecha)
        telef = findViewById(R.id.text_detalle_admin_piloto_telefono)
        email = findViewById(R.id.text_detalle_admin_piloto_email)
        licenc = findViewById(R.id.text_detalle_admin_piloto_licencia)
        tarj = findViewById(R.id.text_detalle_admin_piloto_tarjeta)

        btnEditar = findViewById(R.id.btn_editar_piloto)
        alta_baja = findViewById(R.id.btn_alta_baja_piloto)
        licencia = findViewById(R.id.btn_habilitar_licencia)

        cargarDatos()

        btnEditar.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, EditarUsuarioActivity::class.java).apply {
                putExtra("id", id.toString())
                putExtra("admin", false)
            }
            ctx.startActivity(intent)
        })

        licencia.setOnClickListener(View.OnClickListener {
            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.licencia))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.habili_lic)) { view, _ ->
                        if(!licenciaPilot){
                            service.habilitarLicencia("Bearer "+token, id).enqueue(object : Callback<Any> {
                                override fun onResponse(call: Call<Any>, response: Response<Any>
                                ) {
                                    if (response.code() == 200) {
                                        (ctx as DetallePilotoActivity).recreate()
                                    }
                                }
                                override fun onFailure(call: Call<Any>, t: Throwable) {
                                    Log.i("Error", "Error")
                                    Log.d("Error", t.message!!)
                                }
                            })
                        }
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

            dialog.show()
        })

        alta_baja.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(ctx)
                    .setTitle("")
                    .setMessage(getString(R.string.cambiar_estado_pilot))
                    .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(getString(R.string.cambiar)) { view, _ ->
                        service.estado("Bearer "+token, id).enqueue(object : Callback<DtoUserInfoSpeci> {
                            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
                            ) {
                                if (response.code() == 200) {
                                    (ctx as DetallePilotoActivity).recreate()
                                }
                            }
                            override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
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
        service.detallePiloto("Bearer "+token, id).enqueue(object : Callback<DtoPilot> {
            override fun onResponse(call: Call<DtoPilot>, response: Response<DtoPilot>
            ) {
                if (response.code() == 200) {
                    val fechaN = response.body()?.fechaNacimiento?.split("-")?.get(2)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(1)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(0)
                    nombre.text = response.body()?.nombreCompleto
                    user.text = response.body()?.username
                    fecha.text = fechaN
                    telef.text = response.body()?.telefono
                    email.text = response.body()?.email
                    tarj.text = response.body()?.tarjeta
                    licenciaPilot = response.body()?.licencia!!
                    altaPilot = response.body()?.alta!!
                    if(licenciaPilot){
                        licenc.text = getString(R.string.con_licencia)
                    }else{
                        licenc.text = getString(R.string.sin_licencia)
                    }
                    if(licenciaPilot){
                        licencia.visibility = View.INVISIBLE
                    }
                    if(altaPilot) alta_baja.text = getString(R.string.dar_de_baja) else alta_baja.text = getString(R.string.dar_de_alta)
                }
            }
            override fun onFailure(call: Call<DtoPilot>, t: Throwable) {
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