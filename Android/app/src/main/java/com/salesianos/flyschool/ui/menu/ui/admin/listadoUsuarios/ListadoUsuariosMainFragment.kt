package com.salesianos.flyschool.ui.menu.ui.admin.listadoUsuarios

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.salesianos.flyschool.R
import com.salesianos.flyschool.ui.detalle.admin.registroUsuario.RegistroUsuariosActivity
import com.salesianos.flyschool.ui.menu.MenuActivity


class ListadoUsuariosMainFragment : Fragment() {
    lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_listado_usuarios_main, container, false)

        val boton: Button = root.findViewById(R.id.btn_direc_registro_usuario)

        boton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RegistroUsuariosActivity::class.java)
            startActivity(intent)
        })

        return root
    }


}