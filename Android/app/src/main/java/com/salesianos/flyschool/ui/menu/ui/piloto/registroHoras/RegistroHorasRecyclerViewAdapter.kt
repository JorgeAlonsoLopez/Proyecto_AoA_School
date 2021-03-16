package com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
       // holder.idView.text = item.fecha

    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoRegistro>) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)

    }
}