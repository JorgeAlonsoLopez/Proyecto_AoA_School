package com.salesianos.flySchool

import com.salesianos.flySchool.entity.*
import com.salesianos.flySchool.service.AeronaveService
import com.salesianos.flySchool.service.FotoAeronaveServicio
import com.salesianos.flySchool.service.RegistroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.annotation.PostConstruct

@Component
class InitDataComponent {

    @Autowired
    lateinit var serviceAero: AeronaveService
    @Autowired
    lateinit var serviceFoto: FotoAeronaveServicio
    @Autowired
    lateinit var segistro: RegistroService

    @PostConstruct
    fun initData() {

        var a = Aeronave("EC-000", "Pilatus", "PC-21", "Rotax 980", 190.00, 900.00, 160.00, 120.00, 45.00)
        serviceAero.save(a)


    }

}