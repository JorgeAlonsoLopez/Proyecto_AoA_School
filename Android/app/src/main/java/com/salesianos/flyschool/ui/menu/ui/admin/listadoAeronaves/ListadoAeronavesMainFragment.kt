package com.salesianos.flyschool.ui.menu.ui.admin.listadoAeronaves

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.salesianos.flyschool.R
import com.salesianos.flyschool.ui.detalle.admin.registroAeronave.RegistroAeronaveActivity
import com.salesianos.flyschool.ui.detalle.admin.registroUsuario.RegistroUsuariosActivity

class ListadoAeronavesMainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_listado_aeronaves_main, container, false)

        val boton: Button = root.findViewById(R.id.btn_direc_nueva_aeronave)

        boton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RegistroAeronaveActivity::class.java)
            startActivity(intent)
        })
        return root
    }


}