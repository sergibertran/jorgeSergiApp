package com.example.jorgesergiapp

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import com.example.jorgesergiapp.models.Usuario

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
            val password = etPassword.text.toString().trim()
            val correo = etCorreo.text.toString().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                registerEntrenador(nombre, password, correo,0)
            }
        }
    }

    private fun registerEntrenador(nombre: String, password: String,correo: String, admin: Int) {
        val db = FirebaseFirestore.getInstance()
        val user = Usuario(nombre, password,correo, 0)

        db.collection("usuarios")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                // Puedes redirigir al usuario a otra pantalla si lo deseas
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}