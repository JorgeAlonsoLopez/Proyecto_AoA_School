package com.salesianos.flySchool.security.jwt

import com.salesianos.flySchool.entity.Usuario
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Clase encargada de la generación del token y su validación
 */
@Component
class JwtTokenProvider(
        @Value("\${jwtSecreto}") private var jwtSecreto : String,
        @Value("\${jwtDuracionToken}") private var jwtDuracionToken : Long,
        @Value("\${jwtDuracionRefreshToken}") private var jwtDuracionRefreshToken : Long
){

    companion object {
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_TYPE = "JWT"
    }

    private val parser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.toByteArray())).build()

    private val logger : Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    /**
     * Método que se encarga de generar el token para el usuario autenticado
     */
    fun generateToken(authentication : Authentication) : String {
        val user : Usuario = authentication.principal as Usuario
        return generateTokens(user, false)
    }

    /**
     * Método que genera el token para un usuario particular
     */
    fun generateToken(user : Usuario) = generateTokens(user, false)

    /**
     * Se genera el token de refresco
     */
    fun generateRefreshToken(authentication: Authentication) : String {
        val user : Usuario = authentication.principal as Usuario
        return generateTokens(user, true)
    }

    fun generateRefreshToken(user : Usuario) = generateTokens(user, true)

    /**
     * Genera el token para un uaurio, haciendo uso del token de refresco
     */
    private fun generateTokens(user : Usuario, isRefreshToken : Boolean) : String {
        val tokenExpirationDate =
            Date.from(Instant.now().plus(if (isRefreshToken) jwtDuracionRefreshToken else jwtDuracionToken, ChronoUnit.DAYS))
        val builder = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecreto.toByteArray()), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", TOKEN_TYPE)
            .setSubject(user.id.toString())
            .setExpiration(tokenExpirationDate)
            .setIssuedAt(Date())
            .claim("refresh", isRefreshToken)

        if (!isRefreshToken) {
            builder
                .claim("fullname", user.nombreCompleto)
                .claim("roles", user.roles.joinToString())

        }
        return builder.compact()
    }

    /**
     * Método encargado de proporcionar un usuario a partir de un token
     */
    fun getUserIdFromJWT(token: String): UUID = UUID.fromString(parser.parseClaimsJws(token).body.subject)

    /**
     * Método encargado de validar un token de refresco
     */
    fun validateRefreshToken(token : String) = validateToken(token, true)

    /**
     * Método cuya función es la de validar un token de autenticación
     */
    fun validateAuthToken(token : String) = validateToken(token, false)

    /**
     * Valida un token, comprobando la firma del mismo y lanzando la excepción oportuna en el casp
     * de que no se produzca la validación de dicho token
     */
    private fun validateToken(token : String, isRefreshToken: Boolean) : Boolean {
        try {
            val claims = parser.parseClaimsJws(token)
            if (isRefreshToken != claims.body["refresh"])
                throw UnsupportedJwtException("No se ha utilizado el token apropiado")
            return true
        } catch (ex : Exception) {
            with(logger) {
                when (ex) {
                    is SignatureException -> info("Error en la firma del token ${ex.message}")
                    is MalformedJwtException -> info("Token malformado ${ex.message}")
                    is ExpiredJwtException -> info("Token expirado ${ex.message}")
                    is UnsupportedJwtException -> info("Token no soportado ${ex.message}")
                    is IllegalArgumentException -> info("Token incompleto (claims vacío) ${ex.message}")
                    else -> info("Error indeterminado")
                }
            }

        }

        return false

    }
}