package com.example.jorgesergiapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jorgesergiapp.models.Usuario
import com.google.firebase.firestore.FirebaseFirestore

class loginApp : AppCompatActivity() {
    object UserManager {
        var usuario: Usuario? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val db = FirebaseFirestore.getInstance()
        //**BORRAR** main();
        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val usuarioEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
            val usuarioPass = findViewById<EditText>(R.id.editTextTextPassword)

            //**BORRAR** val usuarioObjeto = Usuario(usuarioEditText.text.toString(), "",0,0)
            // Guardar información del usuario en el Singleton
            // UserManager.usuario = usuarioObjeto
            // Consulta la colección "Entrenador" para el correo proporcionado
            db.collection("Entrenador")
                .whereEqualTo("Correo", usuarioEditText.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val storedPassword = document.getString("contraseña")
                        if (storedPassword == usuarioPass.text.toString()) {
                            // Contraseña coincide, redirige al homeApp
                            val intent = Intent(this, homeApp::class.java)
                            startActivity(intent)
                            return@addOnSuccessListener
                        }
                    }
                    // Si no se encuentra un usuario coincidente, muestra un mensaje de error
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, homeApp::class.java)
                    startActivity(intent)

                }
            val buttonClickRegister = findViewById<Button>(R.id.button3)
            buttonClickRegister.setOnClickListener {
                val intent = Intent(this, register::class.java)
                startActivity(intent)
            }


        }



    }
}