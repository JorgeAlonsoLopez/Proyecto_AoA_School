package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserInfo
import com.salesianos.flyschool.ui.detalle.admin.detalleAdmin.DetalleAdminActivity
import com.salesianos.flyschool.ui.detalle.admin.detallePiloto.DetallePilotoActivity


class ListaUsuariosRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoUserInfo>
) : RecyclerView.Adapter<ListaUsuariosRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lista_usuarios, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nombre.text = item.nombreCompleto
        holder.tipo.text = item.tipo
        if(item.alta){
            holder.baja.visibility = INVISIBLE
            holder.alta.visibility = VISIBLE
        }else{
            holder.alta.visibility = INVISIBLE
            holder.baja.visibility = VISIBLE
        }

        holder.nombre.setOnClickListener(View.OnClickListener {
            if(item.tipo == "Admin" ){
                val intent = Intent(activity, DetalleAdminActivity::class.java).apply {
                    putExtra("id", item.id)
                }
                activity.startActivity(intent)
            }else{
                val intent = Intent(activity, DetallePilotoActivity::class.java).apply {
                    putExtra("id", item.id)
                }
                activity.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoUserInfo>?) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.text_nombre_completo_usuario_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_usuario_value)
        val alta: ImageView = view.findViewById(R.id.estado_ok_usuario_value)
        val baja: ImageView = view.findViewById(R.id.estado_bad_usuario_value)



    }
}