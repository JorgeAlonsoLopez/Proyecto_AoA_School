package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.ListaUsuariosNotFoundException
import com.salesianos.flySchool.error.UserModifNotFoundException
import com.salesianos.flySchool.error.UserSearchNotFoundException
import com.salesianos.flySchool.repository.AdminRepository
import com.salesianos.flySchool.repository.PilotoRepository
import com.salesianos.flySchool.repository.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

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
                    repository.save(Admin(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email, nuevoUsuario.telefono,
                            nuevoUsuario.nombreCompleto, fecha, mutableSetOf("ADMIN")))
                )
            }else{
                return Optional.of(
                    repository.save(Piloto(nuevoUsuario.username, encoder.encode(nuevoUsuario.password), nuevoUsuario.email, nuevoUsuario.telefono,
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

    override fun findById(id: UUID?) = super.findById(id)

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

    fun listado() : ResponseEntity<List<DtoUserInfo>> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findAll().map{it.toGetDtoUserInfo(it)}
            .takeIf { it.isNotEmpty() } ?: throw ListaUsuariosNotFoundException(Usuario::class.java))
    }

    fun detalle( id: UUID) : ResponseEntity<DtoUserInfoSpeci> {
        return ResponseEntity.status(HttpStatus.OK).body(this.findById(id).map { it.toGetDtoUserInfoSpeci() }
            .orElseThrow { UserSearchNotFoundException(id.toString()) })
    }

    fun cambiarEstado(id: UUID, pilotoService: PilotoService): ResponseEntity<DtoUserInfoSpeci> {

        return ResponseEntity.status(HttpStatus.OK).body(pilotoService.findById(id)
            .map { prod ->
                prod.alta = !prod.alta!!
                pilotoService.save(prod).toGetDtoUserInfoSpeci()
            }.orElseThrow { UserModifNotFoundException(id.toString()) })
    }

    fun editar(user: DtoUserForm, id: UUID): ResponseEntity<DtoUserInfoSpeci> {

        return ResponseEntity.status(HttpStatus.OK).body(this.findById(id)
            .map { fromRepo ->
                fromRepo.email = user.email
                fromRepo.nombreCompleto = user.nombreCompleto
                fromRepo.telefono = user.telefono
                fromRepo.fechaNacimiento = LocalDate.of((user.fechaNacimiento.split("/")[2]).toInt(),(user.fechaNacimiento.split("/")[1]).toInt(),(user.fechaNacimiento.split("/")[0]).toInt())
                this.save(fromRepo).toGetDtoUserInfoSpeci()
            }.orElseThrow { UserModifNotFoundException(id.toString()) })
    }

    fun editPassword(passw: DtoPassword,) {
        var user = Usuario("", "", "", "", "", LocalDate.now(), mutableSetOf(""))
        if(passw.password1 == passw.password2){
            user.password = passw.password1
            this.save(user)
        }
    }

    fun me(user: Usuario): ResponseEntity<Any> {
        if(user.roles.first()=="PILOT"){
            return ResponseEntity.ok().body(PilotoServ.findById(user.id!!).get().toGetDtoUserInfoSpeciPilot())
        }else{
            return ResponseEntity.ok().body(user.toGetDtoUserInfoSpeci())
        }
    }

    fun nuevoUsuario(newUser: DtoUserForm): ResponseEntity<DtoUserInfoSpeci> {

        var fecNac= LocalDate.of((newUser.fechaNacimiento.split("/")[2]).toInt(),(newUser.fechaNacimiento.split("/")[1]).toInt(),(newUser.fechaNacimiento.split("/")[0]).toInt())
        return this.create(newUser, fecNac).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toGetDtoUserInfoSpeci()) }.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${newUser.username} ya existe")
        }

    }


}
