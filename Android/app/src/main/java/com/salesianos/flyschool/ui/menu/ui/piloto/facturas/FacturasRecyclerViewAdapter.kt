package com.salesianos.flyschool.ui.menu.ui.piloto.facturas

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoFacturaCliente


class FacturasRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoFacturaCliente>
) : RecyclerView.Adapter<FacturasRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_facturas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var fechaNac =item.fecha.subSequence(8,10).toString()+"/"+item.fecha.subSequence(5,7).toString()+"/"+item.fecha.subSequence(0,4).toString()+" "+item.fecha.subSequence(11,16).toString()
        holder.nombre.text = item.producto.nombre
        holder.precio.text = item.producto.precio.toString()
        holder.fecha.text = fechaNac

        if(item.producto.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Enseñanza"
        }
    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoFacturaCliente>?) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.text_nombre_factura_piloto_value)
        val fecha: TextView = view.findViewById(R.id.date_compra_factura_piloto_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_factura_piloto_value)
        val precio: TextView = view.findViewById(R.id.text_precio_factura_piloto_value)
    }
}