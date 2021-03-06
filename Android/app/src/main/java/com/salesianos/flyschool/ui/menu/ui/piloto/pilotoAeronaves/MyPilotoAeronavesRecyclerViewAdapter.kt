package com.salesianos.flyschool.ui.menu.ui.piloto.pilotoAeronaves

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.ui.detalle.admin.detalleAeronave.DetalleAeronaveActivity
import com.salesianos.flyschool.ui.detalle.piloto.detalleAeronave.PilotoDetalleAeronaveActivity


class MyPilotoAeronavesRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoAeronaveResp>
) : RecyclerView.Adapter<MyPilotoAeronavesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_piloto_aeronaves_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.marca.text = item.marca
        holder.modelo.text = item.modelo
        holder.matricula.text = item.matricula
        holder.imagen.load(item.fotoURL.url)

        if(item.mantenimiento){
            holder.operativo.visibility = View.INVISIBLE
            holder.mantenimiento.visibility = View.VISIBLE
        }else{
            holder.mantenimiento.visibility = View.INVISIBLE
            holder.operativo.visibility = View.VISIBLE
        }


        holder.imagen.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, PilotoDetalleAeronaveActivity::class.java).apply {
                putExtra("id", item.id)
            }
            activity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int = values.size

    fun setData(listaNueva: List<DtoAeronaveResp>) {
        if (listaNueva != null) {
            values = listaNueva
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val marca: TextView = view.findViewById(R.id.text_marca_lista_Aviones_value)
        val modelo: TextView = view.findViewById(R.id.text_modelo_lista_Aviones_value)
        val matricula: TextView = view.findViewById(R.id.text_matricula_lista_Aviones_value)
        val imagen: ImageView = view.findViewById(R.id.text_foto_lista_Aviones_value)
        val operativo: ImageView = view.findViewById(R.id.estado_ok_lista_Aviones_value)
        val mantenimiento: ImageView = view.findViewById(R.id.estado_bad_lista_Aviones_value)
    }
}