package com.salesianos.flyschool.ui.detalle.admin.registroAeronave

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveForm
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoAeronaveSinFoto
import com.salesianos.flyschool.retrofit.AeronaveService
import com.salesianos.flyschool.ui.detalle.admin.URIPathHelper
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
    val REQUEST_CODE = 200
    lateinit var retrofit: Retrofit
    lateinit var service: AeronaveService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    var edit : Boolean = false
    lateinit var foto : ImageView
    val uriPathHelper = URIPathHelper()
    lateinit var filePath : String
    lateinit var hash : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_aeronave)
        filePath = ""
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

        if(intent.extras?.getString("id") != "N"){
            id = UUID.fromString(intent.extras?.getString("id"))
        }
        edit = intent.extras?.getBoolean("edit")!!


        var btnFoto : Button = findViewById(R.id.btn_foto)
        foto = findViewById(R.id.img_form_aeronave)


        btnFoto.setOnClickListener {
            if(edit){
                service.deletePhoto("Bearer " + token, id, hash).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.code() == 200) {
                        }else if(response.code() == 500){
                            Toast.makeText(applicationContext, getString(R.string.error_matricula), Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, getString(R.string.aviso_error_general), Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.i("Error", "Error eliminar imagen")
                        Log.d("Error", t.message!!)
                    }
                })
            }
            openGalleryForImage()
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

        if(edit){
            service.aeronaveId("Bearer " + token, id).enqueue(object :
                Callback<DtoAeronaveResp> {
                override fun onResponse(call: Call<DtoAeronaveResp>, response: Response<DtoAeronaveResp>
                ) {
                    if (response.code() == 200) {
                        marca.setText(response.body()?.marca)
                        modelo.setText(response.body()?.modelo)
                        matricula.setText(response.body()?.matricula)
                        motor.setText(response.body()?.motor)
                        potencia.setText(response.body()?.potencia.toString())
                        autonomia.setText(response.body()?.autonomia.toString())
                        max.setText(response.body()?.velMax.toString())
                        min.setText(response.body()?.velMin.toString())
                        crucero.setText(response.body()?.velCru.toString())
                        foto.load(response.body()?.fotoURL!!.url)
                        hash = response.body()?.fotoURL!!.hash
                    }
                }
                override fun onFailure(call: Call<DtoAeronaveResp>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })
        }


        btnSend.setOnClickListener(View.OnClickListener {

            var form: DtoAeronaveForm

            form = DtoAeronaveForm(matricula.text.toString(), marca.text.toString(), modelo.text.toString(),
                motor.text.toString(), potencia.text.toString().toDouble(), autonomia.text.toString().toDouble(),
                max.text.toString().toDouble(), min.text.toString().toDouble(), crucero.text.toString().toDouble()
            )

            if(!edit){
                service.crear("Bearer " + token, form).enqueue(object : Callback<DtoAeronaveSinFoto> {
                    override fun onResponse(call: Call<DtoAeronaveSinFoto>, response: Response<DtoAeronaveSinFoto>) {
                        if (response.code() == 201) {
                            id = UUID.fromString(response.body()?.id)
                            if(filePath != "") cargarImagen(id, filePath) else (ctx as RegistroAeronaveActivity).finish()
                        }else{
                            Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<DtoAeronaveSinFoto>, t: Throwable) {
                        Log.i("Error", "Error")
                        Log.d("Error", t.message!!)
                    }
                })
            }else{
                service.editar("Bearer " + token, form, id).enqueue(object : Callback<DtoAeronaveSinFoto> {
                    override fun onResponse(call: Call<DtoAeronaveSinFoto>, response: Response<DtoAeronaveSinFoto>) {
                        if (response.code() == 200) {
                            if(filePath != "") cargarImagen(id, filePath) else  (ctx as RegistroAeronaveActivity).finish()
                        }else{
                            Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<DtoAeronaveSinFoto>, t: Throwable) {
                        Log.i("Error", "Error")
                        Log.d("Error", t.message!!)
                    }
                })
            }
        })

        btnCancel.setOnClickListener(View.OnClickListener { (ctx as RegistroAeronaveActivity).finish() })


    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            foto.setImageURI(data?.data) // handle chosen image
            filePath = uriPathHelper.getPath(this, data?.data!!).toString()
        }
    }

    private fun cargarImagen(id: UUID, filePath: String){

        val file : File = File(filePath)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)


        service.addFoto("Bearer " + token, id ,body).enqueue(object : Callback<DtoAeronaveResp> {
            override fun onResponse(call: Call<DtoAeronaveResp>, response: Response<DtoAeronaveResp>) {
                if (response.code() == 201) {
                    (ctx as RegistroAeronaveActivity).finish()
                }else{
                    Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DtoAeronaveResp>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })
    }




}