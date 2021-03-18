package com.salesianos.flyschool.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.salesianos.flyschool.MainActivity
import com.salesianos.flyschool.R
import com.salesianos.flyschool.poko.DtoUserInfoSpeci
import com.salesianos.flyschool.poko.LoginResponse
import com.salesianos.flyschool.retrofit.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var ctx: Context
    lateinit var retrofit: Retrofit
    lateinit var service: UsuarioService
    val baseUrl = "https://aoa-school.herokuapp.com/"
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val headerView: View = navView.getHeaderView(0)
        var nombreUsuario : TextView = headerView.findViewById(R.id.text_menu_user)
        var nombreCompleto : TextView = headerView.findViewById(R.id.text_menu_nombre)


        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        ctx = this
        service = retrofit.create(UsuarioService::class.java)

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        token = sharedPref.getString("TOKEN", "")!!

        service.me("Bearer "+token).enqueue(object : Callback<DtoUserInfoSpeci> {
            override fun onResponse(call: Call<DtoUserInfoSpeci>, response: Response<DtoUserInfoSpeci>
            ) {
                if (response.code() == 200) {
                    nombreUsuario.text = response.body()?.username
                    nombreCompleto.text = response.body()?.nombreCompleto
                }
            }
            override fun onFailure(call: Call<DtoUserInfoSpeci>, t: Throwable) {
                Log.i("Error", "Error")
                Log.d("Error", t.message!!)
            }
        })




        appBarConfiguration = AppBarConfiguration(
            setOf(
            R.id.tiempoFragment, R.id.detalleUsuarioFragment, R.id.listadoUsuariosMainFragment3, R.id.passwordFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_logout -> {
                salir()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun salir(){
        val intent = Intent(ctx, MainActivity::class.java)
        startActivity(intent)
    }
}