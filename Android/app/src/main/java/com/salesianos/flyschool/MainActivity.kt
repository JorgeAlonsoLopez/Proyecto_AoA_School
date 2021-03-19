package com.salesianos.flyschool

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.salesianos.flyschool.poko.DtoLogin
import com.salesianos.flyschool.poko.LoginResponse
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        ctx = this
        service = retrofit.create(UsuarioService::class.java)


        var btnLogin : Button = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener(View.OnClickListener {

            var username : EditText = findViewById(R.id.inputUser)
            var password : EditText = findViewById(R.id.inputPassw)
            var loginRequest = DtoLogin(username.text.toString(), password.text.toString())

            service.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if (response.code() == 200) {
                        val sharedPref = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
                        token = response.body()?.token!!
                        with(sharedPref.edit()) {
                            putString("TOKEN", response.body()?.token)
                            commit()
                        }
                        username.text.clear()
                        password.text.clear()
                        val intent = Intent(ctx, MenuActivity::class.java)
                        startActivity(intent)
                    }else if (response.code() == 401){
                        Toast.makeText(applicationContext,"El usuario o la contraseña no son correctos.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })

        })


    }

}