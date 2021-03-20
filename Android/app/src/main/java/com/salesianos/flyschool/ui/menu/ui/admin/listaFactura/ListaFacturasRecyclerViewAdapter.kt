package com.salesianos.flyschool.ui.menu.ui.admin.listaFactura

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoProductoEspecf
import java.util.*


class ListaFacturasRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoFacturaAdmin>
) : RecyclerView.Adapter<ListaFacturasRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lista_facturas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var fechaNac =item.fecha.split("-")?.get(2)+"/"+item.fecha.split("-")?.get(1)+"/"+item.fecha.split("-")?.get(0)
        holder.nombre.text = item.producto.nombre
        holder.precio.text = item.producto.precio.toString()
        holder.comprador.text = item.comprador.nombreCompleto
        holder.fecha.text = fechaNac

        if(item.producto.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Enseñanza"
        }
    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoFacturaAdmin>?) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.text_nombre_factura_value)
        val comprador: TextView = view.findViewById(R.id.text_comprador_factura_value)
        val fecha: TextView = view.findViewById(R.id.date_compra_factura_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_factura_value)
        val precio: TextView = view.findViewById(R.id.text_precio_factura_value)


    }
}