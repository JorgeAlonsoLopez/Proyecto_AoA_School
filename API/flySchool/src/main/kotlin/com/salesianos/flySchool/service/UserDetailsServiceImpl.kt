package com.salesianos.flySchool.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailsServiceImpl(

    private val usuarioServicio: UsuarioService

) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails =
        usuarioServicio.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Usuario $username no encontrado")
        }
}