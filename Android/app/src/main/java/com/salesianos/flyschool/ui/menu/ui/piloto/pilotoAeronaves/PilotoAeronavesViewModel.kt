package com.salesianos.flyschool.ui.menu.ui.piloto.pilotoAeronaves

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salesianos.flyschool.poko.DtoRegistro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PilotoAeronavesViewModel : ViewModel() {

    var baseUrl = "https://api.themoviedb.org/3/"
    var retrofit: Retrofit
 //   var filmService: FilmService

    private var _registros = MutableLiveData<List<DtoRegistro>>()

    val actores: LiveData<List<DtoRegistro>>
        get() = _registros

    init {
        _registros.value = listOf()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
      //  filmService = retrofit.create(FilmService::class.java)
        getRegistros()
    }

    private fun getRegistros() {

    }
}