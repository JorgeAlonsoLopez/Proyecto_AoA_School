package com.salesianos.flyschool.ui.menu.ui.admin.listaProductos

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
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoProductoEspecf
import com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves.ListaAeronavesViewModel
import com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves.ListadoAeronavesRecyclerViewAdapter


class ListaProductosFragment : Fragment() {

    lateinit var list: List<DtoProductoEspecf>
    lateinit var adapterProductos: ListaProductosRecyclerViewAdapter
    lateinit var viewModel: ListaProductosViewModel
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_productos_list, container, false)

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        viewModel = ViewModelProvider(this).get(ListaProductosViewModel::class.java)

        list = listOf()
        adapterProductos = ListaProductosRecyclerViewAdapter(activity as Context, list)

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

    override fun onResume() {
        super.onResume()
        viewModel.reload()
    }


}