package com.salesianos.flyschool.ui.menu.ui.admin.listaProductos

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.ui.detalle.admin.detalleProducto.DetalleProductoActivity
import com.salesianos.flyschool.ui.detalle.admin.registroProducto.RegistroProductoActivity
import java.util.*

class ListaProductosRecyclerViewAdapter(
    private var activity: Context,
    private var values: List<DtoProductoEspecf>
) : RecyclerView.Adapter<ListaProductosRecyclerViewAdapter.ViewHolder>() {

    lateinit var id : UUID

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lista_productos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nombre.text = item.nombre
        holder.precio.text = item.precio.toString()
        holder.horas.text = item.horasVuelo.toString()
        id = UUID.fromString(item.id)

        if(item.alta){
            holder.estado.text = "Alta"
        }else{
            holder.estado.text = "Baja"
        }

        if(item.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Enseñanza"
        }


        holder.btn_editar.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RegistroProductoActivity::class.java).apply {
                putExtra("id", item.id)
                putExtra("edit", true)
            }
            activity.startActivity(intent)
        })


        holder.btn_opt.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, DetalleProductoActivity::class.java).apply {
                putExtra("id", item.id)
            }
            activity.startActivity(intent)
        })

    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoProductoEspecf>?) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.text_nombre_paquete_admin_value)
        val horas: TextView = view.findViewById(R.id.text_horas_paquete_admin_value)
        val estado: TextView = view.findViewById(R.id.text_estado_producto_value)
        val precio: TextView = view.findViewById(R.id.text_precio_producto_admin_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_paquete_admin_value)
        val btn_editar: Button = view.findViewById(R.id.btn_redirec_editar_producto)
        val btn_opt: Button = view.findViewById(R.id.btn_redirec_opt_producto)
    }
}