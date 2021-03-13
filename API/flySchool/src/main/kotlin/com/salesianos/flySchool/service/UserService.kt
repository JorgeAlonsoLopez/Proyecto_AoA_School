package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.DtoUserForm
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.repository.AdminRepository
import com.salesianos.flySchool.repository.PilotoRepository
import com.salesianos.flySchool.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UsuarioService(
    private val encoder: PasswordEncoder,
    private val AdminServ : AdminService,
    private val PilotoServ : PilotoService,
) : BaseService<Usuario, UUID?, UsuarioRepository>(){

    override fun save(t: Usuario): Usuario {
        return super.save(t)
    }

    fun create(nuevoUsuario : DtoUserForm, fecha:LocalDate): Optional<Usuario> {
        if (findByUsername(nuevoUsuario.username).isPresent){
            return Optional.empty()
        }else{
            if(nuevoUsuario.tarjetaCredito == ""){
                return Optional.of(
                    AdminServ.save(Admin(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email, nuevoUsuario.telefono,
                            nuevoUsuario.nombreCompleto, fecha, mutableSetOf("ADMIN")))
                )
            }else{
                return Optional.of(
                    PilotoServ.save(Piloto(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email, nuevoUsuario.telefono,
                            nuevoUsuario.nombreCompleto, fecha, mutableSetOf("PILOT"), nuevoUsuario.tarjetaCredito))
                )
            }
        }
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
