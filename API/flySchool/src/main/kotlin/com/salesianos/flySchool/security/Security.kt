package com.salesianos.flySchool.security


import com.salesianos.flySchool.security.jwt.JwtAuthenticationEntryPoint
import com.salesianos.flySchool.security.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigurePasswordEncoder() {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

}


@Configuration
class ConfigureCors() {

    @Bean
    fun corsConfigurer()  = object : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
        }
    }


}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers( "/h2-console/**").permitAll()
            .antMatchers(POST, "/auth/login", "/auth/token").permitAll()
            .antMatchers(PUT, "/usuario/password").hasAnyRole("PILOT","ADMIN")
            .antMatchers(GET,  "/usuario/me", "/aeronave/{id}", "usuario/piloto/{id}").hasAnyRole("PILOT","ADMIN")
            .antMatchers(POST, "/user/", "/aeronave/**", "/producto/**", "/auth/register").hasRole("ADMIN")
            .antMatchers(DELETE, "/aeronave/**", "/producto/**").hasRole("ADMIN")
            .antMatchers(PUT, "/aeronave/**", "/producto/**", "/usuario/{id}/", "/usuario/{id}/est", "usuario/licencia/{id}").hasRole("ADMIN")
            .antMatchers(GET, "/aeronave/", "/factura/", "/producto/",
                "/producto/{id}", "/registro/", "/usuario/", "/usuario/{id}", "usuario/filtro").hasRole("ADMIN")

            .antMatchers(GET, "/aeronave/alta", "/factura/user", "/producto/alta/{licencia}",
                "/registro/user").hasRole("PILOT")
            .antMatchers(POST, "/factura/{id}", "/registro/{id}").hasRole("PILOT")


        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.headers().frameOptions().disable()

        // @formatter:on
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}