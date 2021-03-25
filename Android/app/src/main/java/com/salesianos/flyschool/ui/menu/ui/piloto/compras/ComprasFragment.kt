package com.salesianos.flyschool.ui.menu.ui.piloto.compras

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
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.ui.menu.MenuActivity

class ComprasFragment : Fragment() {

    lateinit var list: List<DtoProductoEspecf>
    lateinit var adapterProductos: ComprasRecyclerViewAdapter
    lateinit var viewModel: ComprasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compras_list, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_listado_productos)

        viewModel = ViewModelProvider(this).get(ComprasViewModel::class.java)

        list = listOf()
        adapterProductos = ComprasRecyclerViewAdapter(activity as Context, list, viewModel)

        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapterProductos
        }

        viewModel.productos.observe(viewLifecycleOwner, Observer {
            listaNueva -> list = listaNueva
            adapterProductos.setData(listaNueva)
        })

        return view
    }


}