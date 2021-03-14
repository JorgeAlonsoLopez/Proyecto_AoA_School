package com.salesianos.flyschool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.salesianos.flyschool.ui.menu.MenuActivity

class MainActivity : AppCompatActivity() {
    lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctx = this


        var btnLogin : Button = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, MenuActivity::class.java)
            startActivity(intent)
        })


    }
}