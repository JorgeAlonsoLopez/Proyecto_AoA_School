package com.salesianos.flyschool.ui.detalle.admin.registroUsuario

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserForm
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegistroUsuariosActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var  fecha : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuarios)

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

        var nombre : EditText = findViewById(R.id.input_new_usuario_nombre)
        var user : EditText = findViewById(R.id.input_new_usuario_usuario)
        fecha = findViewById(R.id.input_new_usuario_fecha)
        var telef : EditText = findViewById(R.id.input_new_usuario_telef)
        var email : EditText = findViewById(R.id.input_new_usuario_correo)
        var tarjeta : EditText = findViewById(R.id.input_new_usuario_tarjeta)
        var tarjetaText : TextView = findViewById(R.id.textView34)

        var radioAdmin : RadioButton = findViewById(R.id.radio_new_user_admin)
        var radioPilot : RadioButton = findViewById(R.id.radio_new_user_pilot)

        var btnSend : Button = findViewById(R.id.btn_confirm_new_usuario)
        var btnCancel : Button = findViewById(R.id.btn_cancel_new_usuario)
        val radioGroup = findViewById<View>(R.id.radioGroup_new_user) as RadioGroup

        fecha.setOnClickListener { showDatePickerDialog() }

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if(radioAdmin.isChecked){
                    tarjeta.visibility = View.INVISIBLE
                    tarjetaText.visibility = View.INVISIBLE
                }else{
                    tarjeta.visibility = View.VISIBLE
                    tarjetaText.visibility = View.VISIBLE
                }
            }
        })


        var form : DtoUserForm
        supportActionBar?.hide()


        btnSend.setOnClickListener(View.OnClickListener {

            if(user.text.toString().isNotBlank() && email.text.toString().isNotBlank() && telef.text.toString().isNotBlank() && fecha.text.toString().isNotBlank()) {
                if(radioAdmin.isChecked || radioPilot.isChecked){
                    if (radioAdmin.isChecked) {
                        form = DtoUserForm(user.text.toString(), "1234", email.text.toString(), telef.text.toString(), nombre.text.toString(), fecha.text.toString(), "")
                    } else {
                        form = DtoUserForm(user.text.toString(), "1234", email.text.toString(), telef.text.toString(), nombre.text.toString(), fecha.text.toString(), tarjeta.text.toString())
                    }

                    service.nuevoUsuario("Bearer " + token, form).enqueue(object : Callback<DtoUserInfoSpeci> {
                        override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
                        ) {
                            if (response.code() == 201) {
                                Toast.makeText(applicationContext, "El usuario se ha credo con éxito", Toast.LENGTH_SHORT).show()
                              //  sendEmail(form.email, form.username)
                                object : CountDownTimer(2000, 1000) {
                                    override fun onFinish() {
                                        nombre.text.clear()
                                        user.text.clear()
                                        telef.text.clear()
                                        fecha.text.clear()
                                        email.text.clear()
                                        tarjeta.text.clear()
                                        val intent = Intent(ctx, MenuActivity::class.java)
                                        startActivity(intent)
                                    }

                                    override fun onTick(millisUntilFinished: Long) {
                                    }
                                }.start()
                            } else if (response.code() == 404) {
                                Toast.makeText(applicationContext, "No se puede registrar a un usuario con un nombre de usuario repatido", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Ha ocurrido un error, revise los datos y si el error prosige, póngase en contacto con el servicio técnico", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                            Log.i("Error", "Error")
                            Log.d("Error", t.message!!)
                        }
                    })
                }else{
                    Toast.makeText(applicationContext, "No se olvide de seleecionar administrador o piloto", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(applicationContext, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }

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

