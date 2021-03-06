package com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaCliente
import com.salesianos.flyschool.poko.DtoPilot
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.retrofit.FacturaService
import com.salesianos.flyschool.retrofit.UsuarioService
import com.salesianos.flyschool.ui.detalle.admin.registroProducto.RegistroProductoActivity
import com.salesianos.flyschool.ui.detalle.piloto.gps.MapsActivity
import com.salesianos.flyschool.ui.detalle.piloto.registroVuelo.RegistroVueloActivity
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.math.floor
import kotlin.math.round


class RegistroHorasMainFragment : Fragment() {

    var tiempo : Double = 0.0
    var permiso : Boolean = true
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    var token:String = ""
    var id : String = ""
    lateinit var horas: TextView
    lateinit var boton: Button
    lateinit var gps: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_registro_horas_main, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_registro)

        horas = root.findViewById(R.id.text_horas_disponibles_value)
        boton = root.findViewById(R.id.btn_direc_registro_vuelo)
      //  gps = root.findViewById(R.id.btn_gps)

        val sharedPref = context?.getSharedPreferences(requireContext().getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(UsuarioService::class.java)

        detalle()

        boton.setOnClickListener(View.OnClickListener {
                val intent = Intent(activity, RegistroVueloActivity::class.java).apply {
                    putExtra("tiempo", horas.text.toString())
                }
                startActivity(intent)
        })

      //  gps.setOnClickListener(View.OnClickListener {
       //     val intent = Intent(activity, MapsActivity::class.java)
       //     startActivity(intent)
      //  })

        return root

    }

    fun detalle(){
        service.me("Bearer "+token).enqueue(object : Callback<DtoUserInfoSpeci> {
            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
            ) {
                if (response.code() == 200) {
                    id = response.body()?.id!!
                    var id2 = UUID.fromString(response.body()?.id)
                    if(response.body()?.rol == "PILOT"){
                        service.detallePiloto("Bearer "+token, id2).enqueue(object : Callback<DtoPilot> {
                            override fun onResponse(call: Call<DtoPilot>, response: Response<DtoPilot>
                            ) {
                                if (response.code() == 200) {
                                    tiempo = response.body()?.horas!!
                                    permiso = response.body()?.alta!!
                                    if (!permiso) boton.isEnabled = false;
                                    var hor = tiempo.toInt()
                                    var min = (((tiempo - hor)*60).toInt())
                                    var min2 = if (min < 10 ) "0"+min.toString() else min.toString()
                                    horas.text = hor.toString()+":"+min2
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
    }

    override fun onResume() {
        super.onResume()
        detalle()
    }


}