package com.salesianos.flyschool.ui.menu.ui.detalle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.salesianos.flyschool.MainActivity
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class DetalleUsuarioFragment : Fragment() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var token: String
    lateinit var id : UUID


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
        val root = inflater.inflate(R.layout.fragment_detalle_usuario, container, false)

        (activity as MenuActivity?)!!.supportActionBar!!.title = "Detalle del usuario"

        val nombre: TextView = root.findViewById(R.id.text_detalle_usuario_nombre)
        val user: TextView = root.findViewById(R.id.text_detalle_usuario_nombreUsuario)
        val fecha: TextView = root.findViewById(R.id.text_detalle_usuario_fecha)
        val email: TextView = root.findViewById(R.id.text_detalle_usuario_email)
        val telefono: TextView = root.findViewById(R.id.text_detalle_usuario_telefono)
        val tarjeta: TextView = root.findViewById(R.id.text_detalle_usuario_tarjeta)
        val tarjetaT: TextView = root.findViewById(R.id.textView8)

        service.me("Bearer "+token).enqueue(object : Callback<DtoUserInfoSpeci> {
            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
            ) {
                if (response.code() == 200) {
                    user.text = response.body()?.username
                    nombre.text = response.body()?.nombreCompleto
                    fecha.text = response.body()?.fechaNacimiento
                    email.text = response.body()?.email
                    telefono.text = response.body()?.telefono
                    id = UUID.fromString(response.body()?.id)

                    if(response.body()?.rol == "ADMIN"){
                        tarjeta.visibility = View.INVISIBLE
                        tarjetaT.visibility = View.INVISIBLE
                    }else{
                        service.detallePiloto("Bearer "+token, id).enqueue(object : Callback<DtoPilot> {
                            override fun onResponse(call: Call<DtoPilot>, response: Response<DtoPilot>
                            ) {
                                if (response.code() == 200) {
                                    tarjeta.visibility = View.VISIBLE
                                    tarjetaT.visibility = View.VISIBLE
                                    tarjeta.text = response.body()?.tarjeta
                                }
                            }
                            override fun onFailure(call: Call<DtoPilot>, t: Throwable) {
                                Log.i("Error", "Error")
                                Log.d("Error", t.message!!)
                            }
                        })
                    }

                }
            }
            override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })

        return root
    }


}