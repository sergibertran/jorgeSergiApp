package com.example.jorgesergiapp.models

import java.io.Serializable

data class Usuario(
    val usuario: String,
    val password: String,
    val foto: Int,
    val email: String,
    val tipoUsuario: Int
) {
    // Constructor secundario sin foto
    constructor(usuario: String, password: String, email: String, tipoUsuario: Int) :
            this(usuario, password, 0, email, tipoUsuario) // Asigna un valor predeterminado a foto (por ejemplo, 0)

    // Constructor secundario con foto como Int
    constructor(
        usuario: String,
        password: String,
        email: String,
        tipoUsuario: Int,
        foto: Int
    ) : this(usuario, password, foto, email, tipoUsuario)
    constructor(
        usuario: String,
        email: String,
        foto: Int
    ) : this(usuario, "", foto, email, 0)
}