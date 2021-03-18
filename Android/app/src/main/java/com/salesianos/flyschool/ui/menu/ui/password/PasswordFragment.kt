package com.salesianos.flyschool.ui.menu.ui.password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoLogin
import com.salesianos.flyschool.poko.DtoPassword
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.poko.LoginResponse
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PasswordFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(UsuarioService::class.java)

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_password, container, false)

        (activity as MenuActivity?)!!.supportActionBar!!.title = "Cambio de contraseña"

        val pass1: EditText = root.findViewById(R.id.intup_new_passw)
        val pass2: EditText = root.findViewById(R.id.input_repetir_passw)
        val boton: Button = root.findViewById(R.id.btn_password)


        boton.setOnClickListener(View.OnClickListener {

            var dtoPassword = DtoPassword(pass1.text.toString(), pass2.text.toString())

            service.contrasenya("Bearer "+token, dtoPassword).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(activity?.applicationContext,"El cambio ha ocurrido de forma satisfactoria.", Toast.LENGTH_SHORT).show()
                    }else if (response.code() == 400){
                        Toast.makeText(activity?.applicationContext,"Las dos contraseña no son idénticas.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.i("Error", "Error")
                    Log.d("Error", t.message!!)
                }
            })

        })


        return root
    }


}