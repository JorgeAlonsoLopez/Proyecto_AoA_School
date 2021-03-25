package com.salesianos.flyschool.ui.detalle.piloto.detalleAeronave

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.retrofit.AeronaveService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class PilotoDetalleAeronaveActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: AeronaveService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piloto_detalle_aeronave)
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

        matricula = findViewById(R.id.text_matricula_detalle_aeronave)
        modelo = findViewById(R.id.text_modelo_detalle_aeronave)
        marca = findViewById(R.id.text_marca_detalle_aeronave)
        motor = findViewById(R.id.text_motor_detalle_aeronave)
        potencia = findViewById(R.id.text_potencia_detalle_aeronave)
        alcance = findViewById(R.id.text_autonomia_detalle_aeronave)
        max = findViewById(R.id.text_maxima_detalle_aeronave)
        min = findViewById(R.id.text_minima_detalle_aeronave)
        crucero = findViewById(R.id.text_crucero_detalle_aeronave)
        foto = findViewById(R.id.img_foto_detalle_aeronave)


        cargarDatos()
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
                }
            }
            override fun onFailure(call: Call<DtoAeronaveResp>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })
    }

}