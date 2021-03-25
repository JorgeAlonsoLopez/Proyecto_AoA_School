package com.salesianos.flyschool.ui.menu.ui.admin.listaRegistros

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoRegistro


class ListaRegistrosRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoRegistro>
) : RecyclerView.Adapter<ListaRegistrosRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lista_registros, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var fechaNac =item.fecha.split("-")?.get(2)+"/"+item.fecha.split("-")?.get(1)+"/"+item.fecha.split("-")?.get(0)
        holder.piloto.text = item.piloto.nombreCompleto
        holder.inicio.text = item.horaInicio
        holder.fin.text = item.horaFin
        holder.matricula.text = item.aeronave.matricula
        holder.fecha.text = fechaNac

        if(item.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Enseñanza"
        }
    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoRegistro>?) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val piloto: TextView = view.findViewById(R.id.text_piloto_registro_admin_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_lista_registro_admin_value)
        val fecha: TextView = view.findViewById(R.id.text_fecha_lista_registro_admin_value)
        val inicio: TextView = view.findViewById(R.id.text_inicio_lista_registro_admin_value)
        val fin: TextView = view.findViewById(R.id.text_fin_lista_registro_admin_value)
        val matricula: TextView = view.findViewById(R.id.Text_registro_horas_matricula_admin_value)

    }
}