package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianos.flyschool.R


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ListaUsuariosRecyclerViewAdapter(
 //   private val values: List<DummyItem>
) : RecyclerView.Adapter<ListaUsuariosRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lista_usuarios, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //  val item = values[position]
      //  holder.idView.text = item.id
    }

    override fun getItemCount(): Int = 0//values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      //  val idView: TextView = view.findViewById(R.id.item_number)
       // val contentView: TextView = view.findViewById(R.id.content)


    }
}