package com.salesianos.flySchool.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * Entidad que hace referencia a los usuario de la aplicación
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Usuario (

    /**
     * Propiedad que hace referencia al nombre de usuario del usuario
     */
    @Column(nullable = false, unique = true)
    private var username : String,

    /**
     * Propiedad que hace referencia a la contaseña
     */
    private var password : String,

    /**
     * Propiedad que hace referencia al email
     */
    @Column(nullable = false, unique = true)
    var email : String,

    /**
     * Propiedad que hace referencia al número de teléfono
     */
    @Column(nullable = false, unique = true)
    var telefono : String,

    /**
     * Propiedad que hace referencia al nombre completo del usuario
     */
    var nombreCompleto : String,

    /**
     * Propiedad que hace referencia a la fecha de nacimiento del usuario
     */
    var fechaNacimiento : LocalDate,

    /**
     * Propiedad que hace referencia al del usuario
     */
    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = mutableSetOf(),

    /**
     * Propiedad que hace referencia al ID del objeto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    /**
     * Propiedad que indica si el usuario no ha expirado
     */
    private val nonExpired: Boolean = true,

    /**
     * Propiedad que indica si el usuario no ha sido bloqueado
     */
    private val nonLocked: Boolean = true,

    /**
     * Propiedad que indica si el usuario está activo
     */
    private val enabled: Boolean = true,

    /**
     * Propiedad que indica si las credenciales del usuario no han expirado
     */
    private val credentialsNonExpired : Boolean = true,


    ) : UserDetails {
    constructor(usuario: String, password: String, email : String , telefono: String, nombreCompleto : String, fechaNacimiento: LocalDate, role: String) :
            this(usuario, password, email, telefono, nombreCompleto, fechaNacimiento, mutableSetOf(role))

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun getPassword() = password
    fun setPassword(pass:String){ this.password=pass}

    override fun getUsername() = username

    override fun isAccountNonExpired(): Boolean = nonExpired

    override fun isAccountNonLocked(): Boolean = nonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled(): Boolean = enabled


}