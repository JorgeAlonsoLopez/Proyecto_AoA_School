package com.salesianos.flyschool.ui.menu.ui.admin.listaFactura

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
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.ui.menu.MenuActivity
import com.salesianos.flyschool.ui.menu.ui.admin.listaProductos.ListaProductosRecyclerViewAdapter
import com.salesianos.flyschool.ui.menu.ui.admin.listaProductos.ListaProductosViewModel

class ListaFacturasFragment : Fragment() {

    lateinit var list: List<DtoFacturaAdmin>
    lateinit var adapterFacturas: ListaFacturasRecyclerViewAdapter
    lateinit var viewModel: ListaFacturaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_facturas_list, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_listado_facturas)

        viewModel = ViewModelProvider(this).get(ListaFacturaViewModel::class.java)

        list = listOf()
        adapterFacturas = ListaFacturasRecyclerViewAdapter(activity as Context, list)

        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapterFacturas
        }

        viewModel.facturas.observe(viewLifecycleOwner, Observer {
            listaNueva -> list = listaNueva
            adapterFacturas.setData(listaNueva)
        })

        return view
    }

}