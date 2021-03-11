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

    @get:NotBlank(message="{usuario.username.blank}")
    @Column(nullable = false, unique = true)
    private var username : String,

    @get:NotBlank(message="{usuario.password.blank}")
    private var password : String,

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

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    private val enabled: Boolean = true,

    private val credentialsNonExpired : Boolean = true,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null

) : UserDetails {
    constructor(usuario: String, password: String, email : String , telefono: String, nombreCompleto : String, fechaNacimiento: LocalDate, role: String) :
            this(usuario, password, email, telefono, nombreCompleto, fechaNacimiento, mutableSetOf(role), true, true, true, true)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired(): Boolean = nonExpired

    override fun isAccountNonLocked(): Boolean = nonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled(): Boolean = enabled

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Usuario
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}