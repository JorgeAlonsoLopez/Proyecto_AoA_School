package com.salesianos.flySchool.service

import com.salesianos.flySchool.dto.*
import com.salesianos.flySchool.entity.Admin
import com.salesianos.flySchool.entity.Piloto
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.ListaUsuariosNotFoundException
import com.salesianos.flySchool.error.UserModifNotFoundException
import com.salesianos.flySchool.error.UserSearchNotFoundException
import com.salesianos.flySchool.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

/**
 * Servicio perteneciente a la entidad Usuario, heredando de BaseService
 * @see Usuario
 */
@Service
class UsuarioService(
    private val encoder: PasswordEncoder,
    private val AdminServ : AdminService,
    private val PilotoServ : PilotoService,
) : BaseService<Usuario, UUID?, UsuarioRepository>(){

    override fun save(t: Usuario): Usuario {
        return super.save(t)
    }

    /**
     * Método que crear un nuevo usuario, comprobando antes que el nombre de usuario no esté repetido
     */
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

    /**
     * Método cuya finalidad es la de buscar un usuario por su nombre de usuario
     */
    fun findByUsername(username : String) = super.repository.findByUsername(username)

    /**
     * Lista todos los usuarios, trnsformando el resultado en un Dto concreto por cada objeto.
     * En caso de encontrar ninguno, lanza la excepción correspondiente
     */
    fun listado() : List<DtoUserInfo> {
        return this.findAll().map{it.toGetDtoUserInfo(it)}
            .takeIf { it.isNotEmpty() } ?: throw ListaUsuariosNotFoundException(Usuario::class.java)
    }

    /**
     * Busca un usuario por ID, devolviendolo en forma de Dto y lanza la excepción correspondiente en caso de no encontrarlo
     */
    fun detalle( id: UUID) : DtoUserInfoSpeci {
        return this.findById(id).map { it.toGetDtoUserInfoSpeci() }
            .orElseThrow { UserSearchNotFoundException(id.toString()) }
    }

    /**
     * Busca un usuario de tipo Piloto por el ID, devolviendolo en forma de Dto y
     * lanza la excepción correspondiente en caso de no encontrarlo o que el usuario no sea un piloto
     */
    fun detallePiloto( id: UUID) : DtoPilot {
        return PilotoServ.findById(id).map { it.toGetDtoUserInfoSpeciPilot() }
            .orElseThrow { UserSearchNotFoundException(id.toString()) }
    }

    /**
     * Busca un usuario de tipo Piloto por el ID y cambia su estado (alta) y
     * lanza la excepción correspondiente en caso de no encontrarlo
     */
    fun cambiarEstado(id: UUID, pilotoService: PilotoService): DtoUserInfoSpeci? {

        return pilotoService.findById(id)
            .map { prod ->
                prod.alta = !prod.alta!!
                pilotoService.save(prod).toGetDtoUserInfoSpeci()
            }.orElseThrow { UserModifNotFoundException(id.toString()) }
    }

    /**
     * Edita un usuario a través de su ID y los datos aportados. En el caso de que el valor de la propiedad tarjeta esté vacía,
     * buscara un usuario de tipo administrador y le establecerá los nuevos datos. En el caso de que no esté vacía,
     * hara lo mismo con un usuario de tipo piloto. En ambos casos lanzará una excepción si no encuentra el usuario en cuestión
     */
    fun editar(user: DtoUserEdit, id: UUID): DtoUserInfoSpeci? {
        if(user.tarjeta == ""){
        return this.findById(id)
            .map { fromRepo ->
                fromRepo.email = user.email
                fromRepo.nombreCompleto = user.nombreCompleto
                fromRepo.telefono = user.telefono
                fromRepo.fechaNacimiento = LocalDate.of((user.fechaNacimiento.split("/")[2]).toInt(),(user.fechaNacimiento.split("/")[1]).toInt(),(user.fechaNacimiento.split("/")[0]).toInt())
                this.save(fromRepo).toGetDtoUserInfoSpeci()
            }.orElseThrow { UserModifNotFoundException(id.toString()) }
        }else{
            return PilotoServ.findById(id)
                .map { fromRepo ->
                    fromRepo.email = user.email
                    fromRepo.nombreCompleto = user.nombreCompleto
                    fromRepo.telefono = user.telefono
                    fromRepo.fechaNacimiento = LocalDate.of((user.fechaNacimiento.split("/")[2]).toInt(),(user.fechaNacimiento.split("/")[1]).toInt(),(user.fechaNacimiento.split("/")[0]).toInt())
                    fromRepo.tarjetaCredito = user.tarjeta
                    PilotoServ.save(fromRepo).toGetDtoUserInfoSpeci()
                }.orElseThrow { UserSearchNotFoundException(id.toString()) }
        }
    }

    /**
     * Método por el se cambia la cantraseña del usuario outenticado
     */
    fun editPassword(passw: DtoPassword,user: Usuario): Boolean {

        if(passw.password1 == passw.password2){
            user.password =  encoder.encode(passw.password1)
            this.edit(user)
            return true
        }else{
            return false
        }
    }

    /**
     * Se obtiene la información de un usuario. Se usará para obtener la información del usuario outenticado
     */
    fun me(user: Usuario) = user.toGetDtoUserInfoSpeci()

    /**
     * Cre un nuevo usuario
     */
    fun nuevoUsuario(newUser: DtoUserForm): Optional<DtoUserInfoSpeci>? {

        var fecNac= LocalDate.of((newUser.fechaNacimiento.split("/")[2]).toInt(),
            (newUser.fechaNacimiento.split("/")[1]).toInt(),(newUser.fechaNacimiento.split("/")[0]).toInt())
        return this.create(newUser, fecNac).map { it.toGetDtoUserInfoSpeci() }
    }

    /**
     * Busca un piloto por Id y en cado de tener la propiedad licencia a false, lo cambia.
     * En caso de encontrar ninguno, lanza la excepción correspondiente
     */
    fun licencia(id: UUID){
        var piloto = PilotoServ.findById(id).map { it }
            .orElseThrow { UserSearchNotFoundException(id.toString()) }

        if(!piloto.licencia){
            piloto.licencia = true
        }
        PilotoServ.edit(piloto)

    }

    /**
     * Busca los usuario por parte del nombre completo
     */
    fun filtroNombre(name: String) = super.repository.findByFullName(name.toLowerCase())

    /**
     * Busca los usuario por parte del nombre completo.
     * En caso de encontrar ninguno, lanza la excepción correspondiente
     */
    fun listadoFiltroNombre(name: String) : List<DtoUserInfo> {
        return this.filtroNombre(name)?.map{it.toGetDtoUserInfo(it)}
                .takeIf { it!!.isNotEmpty() } ?: throw ListaUsuariosNotFoundException(Usuario::class.java)
    }


}
