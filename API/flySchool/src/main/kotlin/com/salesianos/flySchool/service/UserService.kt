package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoUserForm
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UsuarioService(
    private val encoder: PasswordEncoder
) : BaseService<Usuario, UUID?, UsuarioRepository>(){

    override fun save(t: Usuario): Usuario {
        return super.save(t)
    }

    fun create(nuevoUsuario : DtoUserForm): Optional<Usuario> {
        if (findByUsername(nuevoUsuario.username).isPresent)
            return Optional.empty()
        return Optional.of(
            with(nuevoUsuario) {
                val fechaNac = LocalDate.of(nuevoUsuario.fechaNacimiento.split("/")[0].toInt(),
                    nuevoUsuario.fechaNacimiento.split("/")[1].toInt(), nuevoUsuario.fechaNacimiento.split("/")[2].toInt())
                if(nuevoUsuario.tarjetaCredito == ""){
                    super.repository.save(Admin(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email,
                        nuevoUsuario.telefono ,nuevoUsuario.nombreCompleto, fechaNac, mutableSetOf("ADMIN")))
                }else{
                    super.repository.save(Piloto(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email,
                        nuevoUsuario.telefono ,nuevoUsuario.nombreCompleto, fechaNac, mutableSetOf("PILOT"), nuevoUsuario.tarjetaCredito))
                }
            }

        )
    }

    override fun findAll(): List<Usuario> {
        return super.findAll()
    }

    override fun edit(t: Usuario): Usuario {
        return super.edit(t)
    }

    override fun deleteById(id: UUID?) {
        super.deleteById(id)
    }

    override fun delete(t: Usuario) {
        super.delete(t)
    }

    override fun deleteAll() {
        super.deleteAll()
    }

    fun findByUsername(username : String) = super.repository.findByUsername(username)
}
