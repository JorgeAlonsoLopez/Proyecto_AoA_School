package com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoRegistro

class RegistroHorasRecyclerViewAdapter(
    private var activity: Context,
    private var values: List<DtoRegistro>
) : RecyclerView.Adapter<RegistroHorasRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_registro_horas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var date = item.fecha.split("-")[2]+"/"+item.fecha.split("-")[1]+"/"+item.fecha.split("-")[0]
        holder.fecha.text = date
        holder.inicio.text = item.horaInicio.subSequence(0,5).toString()
        holder.fin.text = item.horaFin.subSequence(0,5).toString()
        holder.matricula.text = item.aeronave.matricula
        if(item.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Vuelo de instrucción"
        }

    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoRegistro>) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fecha: TextView = view.findViewById(R.id.text_fecha_lista_registro_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_lista_registro_value)
        val inicio: TextView = view.findViewById(R.id.text_inicio_lista_registro_value)
        val fin: TextView = view.findViewById(R.id.text_fin_lista_registro_value)
        val matricula: TextView = view.findViewById(R.id.Text_registro_horas_matricula_value)
    }
}