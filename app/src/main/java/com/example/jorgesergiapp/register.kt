package com.example.jorgesergiapp

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList

class register : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPassword2: EditText
    private lateinit var btnRegister: Button
    private var users: MutableList<String?> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

         etName = findViewById(R.id.editTextTextEmailAddress)
        etCorreo = findViewById(R.id.editTextTextEmailAddress3)
         etPassword = findViewById(R.id.editTextTextPassword2)
        etPassword2 = findViewById(R.id.editTextTextPassword4)

         btnRegister = findViewById(R.id.button2)

        btnRegister.setOnClickListener {


            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").whereEqualTo("usuario", etName.text.toString().trim())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val user = (document.getString("usuario"))
                        users.add(user)

                    }

                }
            val nombre = etName.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            if(!nombre.isEmpty() && !password.isEmpty() && !correo.isEmpty()){


            if(etPassword.text.toString().equals(etPassword2.text.toString())){
                if(users.size==0){

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                registerEntrenador(nombre, password, correo,0)
            }
            }else{
                    Toast.makeText(this, "Ya existe un usuario con este nombre", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }
            }else{
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerEntrenador(nombre: String, password: String,correo: String, admin: Int) {
        val db = FirebaseFirestore.getInstance()
        val user = Usuario(nombre, password,"", correo,"0")

        db.collection("usuarios")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, loginApp::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}