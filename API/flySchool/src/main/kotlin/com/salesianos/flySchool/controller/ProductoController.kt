package com.salesianos.flySchool.controller

import com.salesianos.flySchool.service.ProductoService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/producto")
class ProductoController(
    private val servicio: ProductoService,
) {




}