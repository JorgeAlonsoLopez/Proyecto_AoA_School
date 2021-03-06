package com.salesianos.flyschool.ui.menu.ui.piloto.compras

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoProductoEspecf



class ComprasRecyclerViewAdapter(
        private var activity: Context,
        private var values: List<DtoProductoEspecf>,
        private var viewModel: ComprasViewModel
) : RecyclerView.Adapter<ComprasRecyclerViewAdapter.ViewHolder>() {
    lateinit var ctx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_compras, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nombre.text = item.nombre
        holder.precio.text = item.precio.toString()
        if(item.tipoLibre){
            holder.tipo.text = "Vuelo libre"
        }else{
            holder.tipo.text = "Enseñanza"
        }
        ctx = this.activity

        holder.shop.setOnClickListener(View.OnClickListener {
            val dialog = AlertDialog.Builder(ctx)
                    .setTitle(ctx.getString(R.string.compra_titulo)+item.nombre)
                    .setMessage(ctx.getString(R.string.compra_cuerpo))
                    .setNegativeButton(ctx.getString(R.string.cancelar)) { view, _ ->
                        view.dismiss()
                    }
                    .setPositiveButton(ctx.getString(R.string.compra_confirm)) { view, _ ->
                        viewModel.comprar(item.id)
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()

            dialog.show()
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
        val nombre: TextView = view.findViewById(R.id.text_nombre_paquete_value)
        val tipo: TextView = view.findViewById(R.id.text_tipo_paquete_value)
        val precio: TextView = view.findViewById(R.id.text_precio_paquete_value)
        val shop: ImageView = view.findViewById(R.id.paquete_shop)

    }
}