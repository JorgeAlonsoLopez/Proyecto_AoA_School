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

    fun generateToken(authentication : Authentication) : String {
        val user : Usuario = authentication.principal as Usuario
        return generateTokens(user, false)
    }

    fun generateToken(user : Usuario) = generateTokens(user, false)

    fun generateRefreshToken(authentication: Authentication) : String {
        val user : Usuario = authentication.principal as Usuario
        return generateTokens(user, true)
    }

    fun generateRefreshToken(user : Usuario) = generateTokens(user, true)

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

    fun getUserIdFromJWT(token: String): UUID = UUID.fromString(parser.parseClaimsJws(token).body.subject)

    fun validateRefreshToken(token : String) = validateToken(token, true)

    fun validateAuthToken(token : String) = validateToken(token, false)

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