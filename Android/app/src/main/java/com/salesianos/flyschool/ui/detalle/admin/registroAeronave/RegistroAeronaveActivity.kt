package com.salesianos.flyschool.ui.detalle.admin.registroAeronave

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveForm
import com.salesianos.flyschool.poko.DtoAeronaveSinFoto
import com.salesianos.flyschool.retrofit.AeronaveService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*


class RegistroAeronaveActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: AeronaveService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_aeronave)

        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ctx = this
        service = retrofit.create(AeronaveService::class.java)

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        var marca : EditText = findViewById(R.id.inpt_new_aeron_marca)
        var modelo : EditText = findViewById(R.id.inpt_new_aeron_modelo)
        var matricula : EditText = findViewById(R.id.inpt_new_aeron_matricula)
        var motor : EditText = findViewById(R.id.inpt_new_aeron_motor)
        var potencia : EditText = findViewById(R.id.inpt_new_aeron_potenc)
        var autonomia : EditText = findViewById(R.id.inpt_new_aeron_autonom)
        var max : EditText = findViewById(R.id.inpt_new_aeron_max)
        var min : EditText = findViewById(R.id.inpt_new_aeron_min)
        var crucero : EditText = findViewById(R.id.inpt_new_aeron_cruc)

        var btnSend : Button = findViewById(R.id.btn_registro_aseronave_ok)
        var btnCancel : Button = findViewById(R.id.btn_registro_aseronave_cancel)


        btnSend.setOnClickListener(View.OnClickListener {

            var form: DtoAeronaveForm

            form = DtoAeronaveForm(matricula.text.toString(), marca.text.toString(), modelo.text.toString(),
                motor.text.toString(), potencia.text.toString().toDouble(), autonomia.text.toString().toDouble(),
                max.text.toString().toDouble(), min.text.toString().toDouble(), crucero.text.toString().toDouble()
            )

            val file = File("/")

            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile)

            service.crear("Bearer " + token, form).enqueue(object : Callback<DtoAeronaveSinFoto> {
                override fun onResponse(call: Call<DtoAeronaveSinFoto>, response: Response<DtoAeronaveSinFoto>) {
                    if (response.code() == 201) {
                                matricula.text.clear()
                                marca.text.clear()
                                modelo.text.clear()
                                motor.text.clear()
                                potencia.text.clear()
                                autonomia.text.clear()
                                min.text.clear()
                                max.text.clear()
                                crucero.text.clear()


                    } else if (response.code() == 404) {
                        Toast.makeText(applicationContext, getString(R.string.aviso_repetipo), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DtoAeronaveSinFoto>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })

        })


    }
}