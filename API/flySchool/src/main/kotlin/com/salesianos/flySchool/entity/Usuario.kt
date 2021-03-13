package com.salesianos.flySchool.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Usuario (

    @Column(nullable = false, unique = true)
    private var username : String,

    private var password : String,

    @Column(nullable = false, unique = true)
    var email : String,

    @Column(nullable = false, unique = true)
    var telefono : String,

    var nombreCompleto : String,

    var fechaNacimiento : LocalDate,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    private val enabled: Boolean = true,

    private val credentialsNonExpired : Boolean = true,


    ) : UserDetails {
    constructor(usuario: String, password: String, email : String , telefono: String, nombreCompleto : String, fechaNacimiento: LocalDate, role: String) :
            this(usuario, password, email, telefono, nombreCompleto, fechaNacimiento, mutableSetOf(role))

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired(): Boolean = nonExpired

    override fun isAccountNonLocked(): Boolean = nonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled(): Boolean = enabled


}