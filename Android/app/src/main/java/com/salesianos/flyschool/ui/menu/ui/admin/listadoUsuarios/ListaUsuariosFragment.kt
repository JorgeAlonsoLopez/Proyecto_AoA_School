package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
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
        activity?.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        if (sharedPref != null) {
            token = sharedPref.getString("TOKEN", "")!!
        }

        viewModel = ViewModelProvider(this).get(ListaUsuariosViewModel::class.java)

        list = listOf()
        adapterUsuarios = ListaUsuariosRecyclerViewAdapter(activity as Context, list)

        val btn_search: Button = view.findViewById(R.id.btn_search)
        val busqueda: EditText = view.findViewById(R.id.input_search)

        btn_search.setOnClickListener {
            filtro(busqueda.text.toString())
        }

        val lista: RecyclerView = view.findViewById(R.id.list)
        lista.layoutManager =  LinearLayoutManager(context)
        lista.adapter = adapterUsuarios


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

    fun filtro(nombre : String){
        super.onResume()
        viewModel.filtro(nombre)
    }

}