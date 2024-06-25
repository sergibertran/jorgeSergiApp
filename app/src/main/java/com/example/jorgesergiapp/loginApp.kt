package com.example.jorgesergiapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.jorgesergiapp.models.Usuario
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore


class loginApp : AppCompatActivity() {
    object UserManager {
        var usuario: Usuario? = null
    }

    lateinit var drawerLayout: DrawerLayout
    lateinit var  actionBarDrawerToggle: ActionBarDrawerToggle
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

            if (usuarioEditText.text.isNullOrBlank() || usuarioPass.text.isNullOrBlank()) {
                Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show()
            }
            db.collection("usuarios")
                .whereEqualTo("password", usuarioEditText.text.toString().trim())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val storedPassword = document.getString("password")
                        if (storedPassword == usuarioPass.text.toString()) {
                            // Contraseña coincide, redirige al homeApp
                            val intent = Intent(this, homeApp::class.java)
                            startActivity(intent)
                            return@addOnSuccessListener
                        }
                    }
                    // Si no se encuentra un usuario coincidente, muestra un mensaje de error
                    Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT)
                        .show()

                   // val intent = Intent(this, homeApp::class.java)
                    //startActivity(intent)

                }

            }
            val buttonClickRegister = findViewById<Button>(R.id.button3)
            buttonClickRegister.setOnClickListener {



                val intent = Intent(this, register::class.java)
                startActivity(intent)
        }
        drawerLayout = findViewById(R.id.myDrawer_layout)
        // init action bar drawer toggle
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)
        // add a drawer listener into  drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // show home icon on the app bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }
}