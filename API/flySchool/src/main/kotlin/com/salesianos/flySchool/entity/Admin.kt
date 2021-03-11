package com.salesianos.flySchool.entity

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Admin (usuario:String, password:String, email:String, telefono:String,
             nombreCompleto:String, fechaNacimiento: LocalDate, roles: MutableSet<String> = HashSet(),



             )
    : Usuario(usuario, password, email, telefono, nombreCompleto,
    fechaNacimiento, roles){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Admin
        if (id != that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }
}