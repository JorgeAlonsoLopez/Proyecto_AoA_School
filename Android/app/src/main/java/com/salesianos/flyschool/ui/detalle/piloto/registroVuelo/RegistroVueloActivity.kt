package com.salesianos.flyschool.ui.detalle.piloto.registroVuelo

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.salesianos.flyschool.R
import java.util.*


class RegistroVueloActivity : AppCompatActivity() {


    var btnInicio: Button? = null
    var btnFin:Button? = null
    var txtInicio: EditText? = null
    var txtFin:EditText? = null
    private  var mHour:Int = 0
    private  var mMinute:Int = 0
    private  var mHour1:Int = 0
    private  var mMinute1:Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_vuelo)
        btnInicio=findViewById(R.id.btn_hora_inicio);
        btnFin=findViewById(R.id.btn_hora_fin);
        txtInicio=findViewById(R.id.input_hora_inicio);
        txtFin=findViewById(R.id.input_hora_fin);



        val users = arrayOf(
            "EC-000",
            "EC-001",
            "EC-002"
        )
        val spin: Spinner = findViewById(R.id.spinner1)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.setAdapter(adapter);




        val clickListener = View.OnClickListener {view ->
            when (view.getId()) {
                R.id.btn_hora_inicio -> inicio()
                R.id.btn_hora_fin -> fin()
            }
        }

    }

    fun inicio (){
        // Get Current Time
        val c: Calendar = Calendar.getInstance()
        mHour1 = c.get(Calendar.HOUR_OF_DAY)
        mMinute1 = c.get(Calendar.MINUTE)

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { view, hourOfDay, minute -> txtInicio!!.setText("$hourOfDay:$minute") },
            mHour1,
            mMinute1,
            false
        )
        timePickerDialog.show()

    }

    fun fin (){
        // Get Current Time
        val c: Calendar = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { view, hourOfDay, minute -> txtFin!!.setText("$hourOfDay:$minute") },
            mHour,
            mMinute,
            false
        )
        timePickerDialog.show()
    }


}