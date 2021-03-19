package com.salesianos.flyschool.ui.menu.ui.admin.listaProductos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.salesianos.flyschool.R
import com.salesianos.flyschool.ui.detalle.admin.registroAeronave.RegistroAeronaveActivity
import com.salesianos.flyschool.ui.detalle.admin.registroProducto.RegistroProductoActivity


class ListaProductosMainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.fragment_lista_productos_main, container, false)

        val boton: Button = root.findViewById(R.id.btn_direc_nuevo_producto)

        boton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RegistroProductoActivity::class.java).apply {
                putExtra("id", "N")
                putExtra("edit", false)
            }
            startActivity(intent)
        })

        return root
    }


}