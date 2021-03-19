package com.salesianos.flyschool.ui.detalle.admin.detalleAeronave

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.AeronaveService
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.editarUsuario.EditarUsuarioActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_aeronave)

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


        var matricula : TextView = findViewById(R.id.text_matricula_detalle_aeronave_admin)
        var modelo : TextView = findViewById(R.id.text_modelo_detalle_aeronave_admin)
        var marca : TextView = findViewById(R.id.text_marca_detalle_aeronave_admin)
        var motor : TextView = findViewById(R.id.text_motor_detalle_aeronave_admin)
        var potencia : TextView = findViewById(R.id.text_potencia_detalle_aeronave_admin)
        var alcance : TextView = findViewById(R.id.text_autonomia_detalle_aeronave_admin)
        var max : TextView = findViewById(R.id.text_maxima_detalle_aeronave_admin)
        var min : TextView = findViewById(R.id.text_minima_detalle_aeronave_admin)
        var crucero : TextView = findViewById(R.id.text_crucero_detalle_aeronave_admin)
        var estado : TextView = findViewById(R.id.text_estado_detalle_aeronave_admin)
        var operatividad : TextView = findViewById(R.id.text_operatividad_detalle_aeronave_admin)
        var foto : ImageView = findViewById(R.id.img_foto_detalle_aeronave_admin)

        var btnEditar : Button = findViewById(R.id.btn_redirect_form_aeronave)
        var btnEliminar : Button = findViewById(R.id.btn_eliminar_aeronave)
        var btnAlta : Button = findViewById(R.id.btn_estado_aeronave)
        var btnMantenimiento : Button = findViewById(R.id.btn_operativo_aeronave)

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
                    if(response.body()?.mantenimiento!!){
                        operatividad.text = "En mantenimiento"
                        btnMantenimiento.text = "Finalizar mantenimiento"
                    }else{
                        operatividad.text = "Operativo"
                        btnMantenimiento.text = "Llevar a mantenimiento"
                    }
                    if(response.body()?.alta!!){
                        estado.text = "Dado de alta"
                        btnAlta.text = "Dar de baja"
                    }else{
                        estado.text = "Dado de baja"
                        btnAlta.text = "Dar de alta"
                    }
                }
            }
            override fun onFailure(call: Call<DtoAeronaveResp>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })

        btnEditar.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, EditarUsuarioActivity::class.java).apply {
                putExtra("id", id.toString())
                putExtra("admin", true)
            }
            ctx.startActivity(intent)
        })



    }
}