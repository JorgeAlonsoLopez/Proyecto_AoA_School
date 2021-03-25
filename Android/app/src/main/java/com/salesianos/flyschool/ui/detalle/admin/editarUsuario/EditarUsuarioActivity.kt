package com.salesianos.flyschool.ui.detalle.admin.editarUsuario

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoUserEdit
import com.salesianos.flyschool.poko.DtoUserForm
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.registroUsuario.DatePickerFragment
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class EditarUsuarioActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var  fecha : EditText
    lateinit var id : UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)

        supportActionBar!!.hide()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ctx = this
        service = retrofit.create(UsuarioService::class.java)

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        id = UUID.fromString(intent.extras?.getString("id"))
        var adm = (intent.extras?.getBoolean("admin"))

        var nombre : EditText = findViewById(R.id.input_editar_usuario_nombre)
        fecha = findViewById(R.id.input_editar_usuario_fecha)
        var telef : EditText = findViewById(R.id.input_editar_usuario_telef)
        var email : EditText = findViewById(R.id.input_editar_usuario_correo)
        var tarjeta : EditText = findViewById(R.id.input_editar_tarjeta)
        var tarjetaText : TextView = findViewById(R.id.text_tarj)

        var btnSend : Button = findViewById(R.id.btn_confirm_editar_usuario)
        var btnCancel : Button = findViewById(R.id.btn_cancel_editar_usuario)

        fecha.setOnClickListener { showDatePickerDialog() }

        if(adm!!){
            tarjeta.visibility = View.INVISIBLE
            tarjetaText.visibility = View.INVISIBLE
        }else{
            tarjeta.visibility = View.VISIBLE
            tarjetaText.visibility = View.VISIBLE
        }

        if(adm!!){
            service.detalle("Bearer " + token, id).enqueue(object :
                Callback<DtoUserInfoSpeci> {
                override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
                ) {
                    if (response.code() == 200) {
                        var fechaNac = response.body()?.fechaNacimiento?.split("-")?.get(2)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(1)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(0)
                        nombre.setText(response.body()?.nombreCompleto)
                        telef.setText(response.body()?.telefono)
                        fecha.setText(fechaNac)
                        email.setText(response.body()?.email)
                    }
                }
                override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })
        }else{
            service.detallePiloto("Bearer " + token, id).enqueue(object :
                Callback<DtoPilot> {
                override fun onResponse(call: Call<DtoPilot>, response: Response<DtoPilot>
                ) {
                    if (response.code() == 200) {
                        var fechaNac = response.body()?.fechaNacimiento?.split("-")?.get(2)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(1)+"/"+response.body()?.fechaNacimiento?.split("-")?.get(0)
                        nombre.setText(response.body()?.nombreCompleto)
                        telef.setText(response.body()?.telefono)
                        fecha.setText(fechaNac)
                        email.setText(response.body()?.email)
                        tarjeta.setText(response.body()?.tarjeta)
                    }
                }
                override fun onFailure(call: Call<DtoPilot>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })
        }

        var form : DtoUserEdit

        btnSend.setOnClickListener(View.OnClickListener {

            if (adm!!) {
                form = DtoUserEdit(email.text.toString(), telef.text.toString(), nombre.text.toString(), fecha.text.toString(), "")
            } else {
                form = DtoUserEdit(email.text.toString(), telef.text.toString(), nombre.text.toString(), fecha.text.toString(), tarjeta.text.toString())
            }

            service.editarUsuario("Bearer " + token, form, id).enqueue(object :
                Callback<DtoUserInfoSpeci> {
                override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(applicationContext, getString(R.string.aviso_exito), Toast.LENGTH_LONG).show()
                        (ctx as EditarUsuarioActivity).finish()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })

        })

        btnCancel.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, MenuActivity::class.java)
            ctx.startActivity(intent)
        })

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        var mes = month + 1
        fecha.setText("$day/$mes/$year")
    }



}