package com.salesianos.flyschool.ui.menu.ui.piloto.registroHoras

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

class RegistroHorasFragment : Fragment() {

    lateinit var list: List<DtoRegistro>
    lateinit var adapter: RegistroHorasRecyclerViewAdapter
    lateinit var viewModel: RegistroHorasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registro_horas_list, container, false)

        viewModel = ViewModelProvider(this).get(RegistroHorasViewModel::class.java)

        list = listOf()
        adapter = RegistroHorasRecyclerViewAdapter(activity as Context, list)


        with(view as RecyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = adapter
        }

        viewModel.actores.observe(viewLifecycleOwner, Observer {
                listaNueva -> list = listaNueva
            adapter.setData(listaNueva)
        })



        return view
    }


}