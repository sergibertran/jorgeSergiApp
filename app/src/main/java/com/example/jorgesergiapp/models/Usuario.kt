package com.example.jorgesergiapp.models

import java.io.Serializable

class Usuario(
    val usuario: String,
    val password: String,
    val foto: Int,
    val tipoUsuario: Int
) : Serializable {
    // No necesitas definir la clase Usuario aquí nuevamente
}