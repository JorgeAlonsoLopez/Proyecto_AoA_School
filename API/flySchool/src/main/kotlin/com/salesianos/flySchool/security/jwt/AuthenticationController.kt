package com.salesianos.flySchool.security.jwt


import com.salesianos.flySchool.dto.DtoUserInfo
import com.salesianos.flySchool.dto.toGetDtoUserInfo
import com.salesianos.flySchool.entity.Usuario
import com.salesianos.flySchool.error.ApiError
import com.salesianos.flySchool.service.UsuarioService
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

/**
 * Controlador encargado del login y la validación del token de refresco
 */
@RestController
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val bearerTokenExtractor: BearerTokenExtractor,
   private val userService: UsuarioService
) {

    @ApiOperation(
        value = "Realizar el inicio de sesión de un usuario",
        notes = "En el caso de que los datos aportados sean correctos, se llevará a cabo el login y se obtendrá " +
                "como resultado el token."
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "OK", response = JwtUserResponse::class),
            ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException::class)
        ]
    )
    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody loginRequest : LoginRequest) : ResponseEntity<JwtUserResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as Usuario
        val jwtToken = jwtTokenProvider.generateToken(user)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

        return ResponseEntity.status(HttpStatus.OK).body(JwtUserResponse(jwtToken, user.toGetDtoUserInfo(user) ))

    }


    @PostMapping("/auth/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponse> {

        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                val userId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val user: Usuario = userService.findById(userId).orElseThrow {
                    UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                }
                val jwtToken = jwtTokenProvider.generateToken(user)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken, user.toGetDtoUserInfo(user) ))
            }
        } catch (ex : Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        // En cualquier otro caso
        return ResponseEntity.badRequest().build()

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal user : Usuario) = user.toGetDtoUserInfo(user)


}

/**
 * Data class encargada de guardar la informción necesaria para el login
 */
data class LoginRequest(

    @ApiModelProperty(value = "Nombre del usuario a logear")
    @NotBlank val username : String,

    @ApiModelProperty(value = "Contraseña del usuario a logear")
    @NotBlank val password: String
)

/**
 * Data class engargada de guardar la respuesta obtenida al realizar un login correcto
 */
data class JwtUserResponse(

    @ApiModelProperty(value = "Token obtenido")
    val token: String,

    @ApiModelProperty(value = "Datos básicos del usuario logeado")
    val user : DtoUserInfo
)