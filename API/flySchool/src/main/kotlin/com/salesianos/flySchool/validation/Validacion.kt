package com.salesianos.flySchool.validation

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 * Clase que nos permite separar las excepciones de validación particulares de los mensajes que mostrarán cada una de ellas,
 * en un fichero externo
 */
@Configuration
class ConfiguracionValidacion {

    /**
     * Función que define el fichero donde es van a encontrar y la codificación de los mensajes de error
     */
    @Bean
    fun messageSource() : MessageSource {
        var messageSource = ReloadableResourceBundleMessageSource()

        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    /**
     * Función que configura el validador predeterminado
     */
    @Bean
    fun getValidator() : LocalValidatorFactoryBean {
        val validator = LocalValidatorFactoryBean()
        validator.setValidationMessageSource(messageSource())
        return validator
    }

}