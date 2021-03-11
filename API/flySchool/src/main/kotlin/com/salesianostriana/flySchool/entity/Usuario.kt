package com.salesianostriana.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Usuario (
    @get:NotBlank(message="{usuario.username.blank}")
    @Column(nullable = false, unique = true)
    var usuario : String,

    @get:NotBlank(message="{usuario.password.blank}")
    var password : String,

    @get:NotBlank(message="{usuario.email.blank}")
    @Column(nullable = false, unique = true)
    var email : String,

    @get:NotBlank(message="{usuario.telefono.blank}")
    @Column(nullable = false, unique = true)
    var telefono : String,

    @get:NotBlank(message="{usuario.nombreCompleto.blank}")
    var nombreCompleto : String,

    @get:NotNull(message="{usuario.fechaNacimiento.null}")
    var fechaNacimiento : LocalDate,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
        ) {
}