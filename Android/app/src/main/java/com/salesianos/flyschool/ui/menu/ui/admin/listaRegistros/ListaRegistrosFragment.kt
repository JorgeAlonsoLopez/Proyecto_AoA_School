package com.salesianos.flyschool.ui.menu.ui.admin.listaRegistros

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.ui.menu.MenuActivity
import com.salesianos.flyschool.ui.menu.ui.admin.listaFactura.ListaFacturaViewModel
import com.salesianos.flyschool.ui.menu.ui.admin.listaFactura.ListaFacturasRecyclerViewAdapter

class ListaRegistrosFragment : Fragment() {

    lateinit var list: List<DtoRegistro>
    lateinit var adapterRegistro: ListaRegistrosRecyclerViewAdapter
    lateinit var viewModel: ListaRegistrosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_registros_list, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_listado_registros)

        viewModel = ViewModelProvider(this).get(ListaRegistrosViewModel::class.java)

        list = listOf()
        adapterRegistro = ListaRegistrosRecyclerViewAdapter(activity as Context, list)

        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapterRegistro
        }

        viewModel.registros.observe(viewLifecycleOwner, Observer {
            listaNueva -> list = listaNueva
            adapterRegistro.setData(listaNueva)
        })

        return view
    }

}