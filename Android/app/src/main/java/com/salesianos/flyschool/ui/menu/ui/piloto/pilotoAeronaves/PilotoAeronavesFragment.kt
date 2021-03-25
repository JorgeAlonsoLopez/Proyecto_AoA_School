package com.salesianos.flyschool.ui.menu.ui.piloto.pilotoAeronaves

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
import com.salesianos.flyschool.poko.DtoAeronaveResp
import com.salesianos.flyschool.poko.DtoRegistro
import com.salesianos.flyschool.ui.menu.MenuActivity
import com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves.ListaAeronavesViewModel
import com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves.ListadoAeronavesRecyclerViewAdapter
import com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras.RegistroHorasRecyclerViewAdapter
import com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras.RegistroHorasViewModel


class PilotoAeronavesFragment : Fragment() {

    lateinit var list: List<DtoAeronaveResp>
    lateinit var adapterAeronaves: MyPilotoAeronavesRecyclerViewAdapter
    lateinit var viewModel: PilotoAeronavesViewModel
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_piloto_aeronaves_list, container, false)
        (activity as MenuActivity?)!!.supportActionBar!!.title = getString(R.string.menu_listado_aeronaves)

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        viewModel = ViewModelProvider(this).get(PilotoAeronavesViewModel::class.java)

        list = listOf()
        adapterAeronaves = MyPilotoAeronavesRecyclerViewAdapter(activity as Context, list)

        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapterAeronaves
        }

        viewModel.aeronaves.observe(viewLifecycleOwner, Observer {
            listaNueva -> list = listaNueva
            adapterAeronaves.setData(listaNueva)
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadAeronaves()
    }

}