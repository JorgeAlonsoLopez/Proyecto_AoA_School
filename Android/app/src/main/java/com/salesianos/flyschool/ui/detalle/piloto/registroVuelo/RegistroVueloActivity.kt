package com.salesianos.flyschool.ui.detalle.piloto.registroVuelo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.poko.DtoRegistroForm
import com.salesianos.flyschool.retrofit.AeronaveService
import com.salesianos.flyschool.retrofit.RegistroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class RegistroVueloActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: AeronaveService
    lateinit var serviceRegistro: RegistroService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var ctx: Context
    lateinit var token: String
    lateinit var id : UUID
    lateinit var lista: List<DtoAeronaveResp>
    lateinit var tiempo: String
    lateinit var select: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_vuelo)
        supportActionBar!!.hide()

        var txtInicio : EditText = findViewById(R.id.input_hora_inicio);
        var txtFin : EditText = findViewById(R.id.input_hora_fin);
        val aeronaves = ArrayList<String>()
        var btn : Button = findViewById(R.id.btn_enviar_registro_horas)

        tiempo = intent.extras?.getString("tiempo")!!

        txtInicio.setOnClickListener(View.OnClickListener {
            showTimePickerDialog(txtInicio)
        })

        txtFin.setOnClickListener(View.OnClickListener {
            showTimePickerDialog(txtFin)
        })

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        ctx = this
        service = retrofit.create(AeronaveService::class.java)
        serviceRegistro = retrofit.create(RegistroService::class.java)

        val sharedPref = ctx.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        service.listadoAlta("Bearer "+token).enqueue(object : Callback<List<DtoAeronaveResp>> {
            override fun onResponse(call: Call<List<DtoAeronaveResp>>, response: Response<List<DtoAeronaveResp>>
            ) {
                if (response.code() == 200) {
                    lista = response.body()!!
                    lista.filter { !it.mantenimiento }.forEach { aeronaves.add(it.matricula) }
                    val spin: Spinner = findViewById(R.id.spinner1)
                    spin?.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, aeronaves)

                    spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                            select = spin.getSelectedItem().toString()
                        }
                        override fun onNothingSelected(arg0: AdapterView<*>?) {
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<DtoAeronaveResp>>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })

        btn.setOnClickListener(View.OnClickListener {
            if(txtInicio.text.isNotBlank() && txtFin.text.isNotBlank()){
                val dto = DtoRegistroForm(txtInicio.text.toString(), txtFin.text.toString())
                if(time(txtInicio.text.toString(), txtFin.text.toString(), tiempo)){
                    if(difference(txtInicio.text.toString(), txtFin.text.toString())){
                        id = UUID.fromString(lista.filter{select == it.matricula}[0].id)
                        serviceRegistro.crear("Bearer "+token, dto, id).enqueue(object : Callback<DtoRegistro> {
                            override fun onResponse(call: Call<DtoRegistro>, response: Response<DtoRegistro>
                            ) {
                                if (response.code() == 201) {
                                    Toast.makeText(ctx,getString(R.string.regisrto_ok), Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                            override fun onFailure(call: Call<DtoRegistro>, t: Throwable) {
                                Log.i("Error", "Error")
                                Log.d("Error", t.message!!)
                            }
                        })
                    }else{
                        Toast.makeText(ctx,getString(R.string.orden_horas), Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(ctx, getString(R.string.insuficiencia_horas), Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext, getString(R.string.aviso_error), Toast.LENGTH_LONG).show()
            }

        })


    }

    fun showTimePickerDialog(v: EditText) {
        val timePicker = TimePickerFragment { onTimeSelected(it,v) }
        timePicker.show(supportFragmentManager, "timePicker")
    }
    private fun onTimeSelected(time: String, v:EditText) {
        var hor = time.split(":")[0].toInt()
        var min = time.split(":")[1].toInt()
        var h = ""
        var m = ""
        if(hor < 10) h = "0$hor" else h = hor.toString()
        if(min < 10) m = "0$min" else m = min.toString()
        v.setText(h+":"+m)
    }

    fun time(start: String, end: String, time :String): Boolean {
        var result : Boolean
        var stratHr = start.split(":")[0].toInt()
        var StartMn = start.split(":")[1].toInt()
        var endHr = end.split(":")[0].toInt()
        var endMn = end.split(":")[1].toInt()
        var timeHr = time.split(":")[0].toInt()
        var timeMn = time.split(":")[1].toInt()
        var min = endMn - StartMn
        var hor: Int
        if(min < 0){
            hor = endHr - stratHr - 1
            min = (60+min)
        }else{
            hor = endHr - stratHr
        }
        if(timeHr > hor){
            result = true
        }else if(timeHr == hor){
            result = timeMn >= min
        }else{
            result = false
        }
        return result
    }

    fun difference(start: String, end: String): Boolean {
        var result : Boolean
        var stratHr = start.split(":")[0].toInt()
        var StartMn = start.split(":")[1].toInt()
        var endHr = end.split(":")[0].toInt()
        var endMn = end.split(":")[1].toInt()

        if(endHr > stratHr){
            result = true
        }else if(endHr == stratHr){
            result = endMn > StartMn
        }else{
            result = false
        }
        return result
    }

}