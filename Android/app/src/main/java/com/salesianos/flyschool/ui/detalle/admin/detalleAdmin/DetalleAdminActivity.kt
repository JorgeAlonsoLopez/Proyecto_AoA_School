package com.salesianos.flyschool.ui.detalle.admin.detalleAdmin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.editarUsuario.EditarUsuarioActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetalleAdminActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    lateinit var nombre : TextView
    lateinit var user : TextView
    lateinit var fecha : TextView
    lateinit var telef : TextView
    lateinit var email : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_admin)
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

        nombre = findViewById(R.id.text_detalle_admin_admin_nombre)
        user = findViewById(R.id.text_detalle_admin_admin_nombreUsuario)
        fecha = findViewById(R.id.text_detalle_admin_admin_fecha)
        telef = findViewById(R.id.text_detalle_admin_admin_telefono)
        email = findViewById(R.id.text_detalle_admin_admin_email)

        var btnEditar : Button = findViewById(R.id.btn_editar_admin)

        cargarDatos()

        btnEditar.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, EditarUsuarioActivity::class.java).apply {
                putExtra("id", id.toString())
                putExtra("admin", true)
            }
            ctx.startActivity(intent)
        })

        }

    fun cargarDatos(){
        service.detalle("Bearer "+token, id).enqueue(object : Callback<DtoUserInfoSpeci> {
            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
            ) {
                if (response.code() == 200) {
                    val fechaN = response.body()?.fechaNacimiento?.split("-")?.get(2)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(1)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(0)
                    nombre.text = response.body()?.nombreCompleto
                    user.text = response.body()?.username
                    fecha.text = fechaN
                    telef.text = response.body()?.telefono
                    email.text = response.body()?.email
                }
            }
            override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
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