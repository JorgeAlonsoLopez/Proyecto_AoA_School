package com.salesianos.flySchool

import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Aeronave
import com.salesianos.flySchool.entity.Usuario
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.annotation.PostConstruct

@Component
class InitDataComponent {

    @PostConstruct
    fun initData() {

        var user= Usuario("pepepe", "123456", "www@ww.com", "123456879", "Pepe Pepe", LocalDate.now(), "USER")

        var admin= Admin("pepepe", "123456", "www@ww.com", "123456879", "Pepe Pepe", LocalDate.now(), mutableSetOf("ADMIN"))

        var demo = Aeronave("", "", "", ", ", 0.0, 0.0, 0.0, 0.0, 0.0, false)

        println("hola")

    }

}