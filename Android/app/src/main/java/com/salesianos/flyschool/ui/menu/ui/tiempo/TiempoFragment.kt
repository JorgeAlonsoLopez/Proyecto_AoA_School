package com.salesianos.flyschool.ui.menu.ui.tiempo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.weather.WeatherResponse
import com.salesianos.flyschool.retrofit.WeatherService
import com.salesianos.flyschool.ui.menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TiempoFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var service: WeatherService
    val baseUrl = "http://api.openweathermap.org/data/2.5/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(WeatherService::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_tiempo, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = "Tiempo"

        val temperatura: TextView = root.findViewById(R.id.text_tiempo_actual)
        val estado: TextView = root.findViewById(R.id.text_tiempo_calidad)
        val humedad: TextView = root.findViewById(R.id.text_tiempo_hum)
        val visibilidad: TextView = root.findViewById(R.id.text_tiempo_visi)
        val viento: TextView = root.findViewById(R.id.text_tiempo_vient)
        val direccion: TextView = root.findViewById(R.id.text_tiempo_direc)

        service.tiempo().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    temperatura.text = response.body()?.main?.temp.toString()
                    estado.text = response.body()?.weather?.first()?.description.toString().capitalize()
                    humedad.text = response.body()?.main?.humidity.toString()
                    visibilidad.text = response.body()?.visibility.toString()
                    viento.text = response.body()?.wind?.speed.toString()
                    direccion.text = response.body()?.wind?.deg.toString()
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })

        return root
    }



}