package com.salesianos.flyschool.ui.menu.ui.piloto.facturas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoFacturaAdmin
import com.salesianos.flyschool.poko.DtoFacturaCliente
import com.salesianos.flyschool.ui.menu.MenuActivity
import com.salesianos.flyschool.ui.menu.ui.admin.listaFactura.ListaFacturaViewModel
import com.salesianos.flyschool.ui.menu.ui.admin.listaFactura.ListaFacturasRecyclerViewAdapter


class FacturasFragment : Fragment() {

    lateinit var list: List<DtoFacturaCliente>
    lateinit var adapterFacturas: FacturasRecyclerViewAdapter
    lateinit var viewModel: FacturasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_facturas_list, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_listado_facturas_cliente)

        viewModel = ViewModelProvider(this).get(FacturasViewModel::class.java)

        list = listOf()
        adapterFacturas = FacturasRecyclerViewAdapter(activity as Context, list)

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