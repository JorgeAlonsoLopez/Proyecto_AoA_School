package com.salesianos.flySchool.controller

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Factura
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Producto
import com.salesianos.flySchool.entity.RegistroVuelo
import com.salesianos.flySchool.error.ListaProductoNotFoundException
import com.salesianos.flySchool.error.ProductoModifNotFoundException
import com.salesianos.flySchool.error.ProductoSearchNotFoundException
import com.salesianos.flySchool.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/registro")
class RegistroController(
        private val service: RegistroService,
        private val aeronaveService: AeronaveService,
        private val pilotoService: PilotoService
) {

    @GetMapping("/")
    fun listado() : ResponseEntity<List<DtoRegistro>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll().map{it.toGetDtoRegistro()}
                .takeIf { it.isNotEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @GetMapping("/user")
    fun listadoUsuario() : ResponseEntity<List<DtoRegistro>> {
        //TODO buscar logeado
        return ResponseEntity.status(HttpStatus.OK).body(service.findByPiloto(piloto).map{it.toGetDtoRegistro()}
                .takeIf { it.isNullOrEmpty() } ?: throw ListaProductoNotFoundException(Producto::class.java))
    }

    @PostMapping("/{id}")
    fun crear(@RequestBody nueva: DtoRegistroForm, @PathVariable id: UUID) : ResponseEntity<DtoRegistro> {
        var piloto = Piloto("", "", "", "", "", LocalDate.now(), mutableSetOf(""), "")
        val aeronave = aeronaveService.findById(id)
        val inicio = LocalTime.of(nueva.horaInicio.split(":")[0].toInt(),nueva.horaInicio.split(":")[1].toInt())
        val fin =LocalTime.of(nueva.horaFin.split(":")[0].toInt(),nueva.horaFin.split(":")[1].toInt())
        if(inicio < fin && aeronave != null){
            var registro = RegistroVuelo(LocalDate.now(),inicio, fin,
                    service.difference(inicio, fin), piloto, aeronave.get(), piloto.licencia!!)
            service.save(registro)
            var tiemp : Double = registro.tiempo.hour.toDouble() + (registro.tiempo.minute.toDouble()/60)
            piloto.horas = piloto.horas!! - tiemp
            pilotoService.save(piloto)
            return ResponseEntity.status(HttpStatus.CREATED).body(registro.toGetDtoRegistro())
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }

    }

}