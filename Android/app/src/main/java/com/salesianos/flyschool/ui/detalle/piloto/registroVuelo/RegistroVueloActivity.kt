package com.salesianos.flyschool.ui.detalle.piloto.registroVuelo

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.salesianos.flyschool.R


class RegistroVueloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_vuelo)

        var txtInicio : EditText = findViewById(R.id.input_hora_inicio);
        var txtFin : EditText = findViewById(R.id.input_hora_fin);

        txtInicio.setOnClickListener(View.OnClickListener {
            showTimePickerDialog(txtInicio)
        })

        txtFin.setOnClickListener(View.OnClickListener {
            showTimePickerDialog(txtFin)
        })

        val users = arrayOf(
            "EC-000",
            "EC-001",
            "EC-002"
        )
        val spin: Spinner = findViewById(R.id.spinner1)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.setAdapter(adapter);




    }

    fun showTimePickerDialog(v: EditText) {
        val timePicker = TimePickerFragment { onTimeSelected(it,v) }
        timePicker.show(supportFragmentManager, "timePicker")
    }
    private fun onTimeSelected(time: String, v:EditText) {
        v.setText("$time")
    }


}