package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

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
import com.salesianos.flyschool.poko.DtoUserInfo


class ListaUsuariosFragment : Fragment() {

    lateinit var list: List<DtoUserInfo>
    lateinit var adapterUsuarios: ListaUsuariosRecyclerViewAdapter
    lateinit var viewModel: ListaUsuariosViewModel
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_usuarios_list, container, false)

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        viewModel = ViewModelProvider(this).get(ListaUsuariosViewModel::class.java)

        list = listOf()
        adapterUsuarios = ListaUsuariosRecyclerViewAdapter(activity as Context, list)

        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapterUsuarios
        }

        viewModel.usuarios.observe(viewLifecycleOwner, Observer {
            listaNueva -> list = listaNueva
            adapterUsuarios.setData(listaNueva)
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadUsuarios()
    }

}