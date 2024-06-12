package com.example.jorgesergiapp

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle

class register : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

         etName = findViewById(R.id.editTextTextEmailAddress)
        etCorreo = findViewById(R.id.editTextTextEmailAddress)
         etPassword = findViewById(R.id.editTextTextPassword2)
         btnRegister = findViewById(R.id.button2)

        btnRegister.setOnClickListener {
            val nombre = etName.text.toString().trim()
            val contraseña = etPassword.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            if (nombre.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                registerEntrenador(nombre, contraseña, correo)
            }
        }
    }
    data class Entrenador(
        val nombre: String = "",
        val contraseña: String = "",
        val correo: String = ""
    )
    private fun registerEntrenador(nombre: String, contraseña: String,correo: String) {
        val db = FirebaseFirestore.getInstance()
        val Entrenador = Entrenador(nombre, contraseña,correo)

        db.collection("Entrenador")
            .add(Entrenador)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                // Puedes redirigir al usuario a otra pantalla si lo deseas
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}