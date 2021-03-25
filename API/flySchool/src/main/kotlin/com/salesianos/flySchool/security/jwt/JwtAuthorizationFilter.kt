package com.salesianos.flySchool.security.jwt

import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.service.UsuarioService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Clase encargada de extrer el ID del usuario logeado a partir del token proporcionado y el tratamiento de las excepciones pertinentes
 * en el caso de que el token no exista, no sea válido o no se encuentre el usuario en la base de datos
 */
@Component
class JwtAuthorizationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userService: UsuarioService,
    private val bearerTokenExtractor : BearerTokenExtractor
) : OncePerRequestFilter() {

    private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            bearerTokenExtractor.getJwtFromRequest(request).ifPresent { token ->
                if (jwtTokenProvider.validateAuthToken(token)) {
                    val userId = jwtTokenProvider.getUserIdFromJWT(token)
                    val user : Usuario = userService.findById(userId).orElseThrow {
                        UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                    }
                    val authentication = UsernamePasswordAuthenticationToken(user, user.roles, user.authorities)
                    authentication.details = WebAuthenticationDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication

                }
            }
            filterChain.doFilter(request, response)
        } catch (ex : Exception) {
            log.info("No se ha podido establecer la autenticación del usuario en el contexto de seguridad")
            log.info(ex.message)
            throw UsernameNotFoundException(ex.message)
        }

    }


}

/**
 * Clase cuya función es la de extraer el token del encabezado de la petición
 */
@Service
class BearerTokenExtractor {

    fun getJwtFromRequest(request: HttpServletRequest) : Optional<String> {
        val bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX))
            Optional.of(bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length, bearerToken.length)) else Optional.empty()

    }

}