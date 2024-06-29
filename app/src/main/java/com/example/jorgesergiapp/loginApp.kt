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
import com.example.jorgesergiapp.userApp.Companion.prefs
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class loginApp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupViews()
        setupDrawer()

        // Verificar si ya hay un usuario guardado en prefs
        checkUserValues()
    }

    private fun setupViews() {
        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val usuarioEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
            val usuarioPass = findViewById<EditText>(R.id.editTextTextPassword)

            if (usuarioEditText.text.isNullOrBlank() || usuarioPass.text.isNullOrBlank()) {
                Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("usuarios")
                .whereEqualTo("usuario", usuarioEditText.text.toString().trim())
                .whereEqualTo("password", usuarioPass.text.toString().trim())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val id = document.id
                        val storedPassword = document.getString("password")
                        val storedUser = document.getString("usuario")
                        if (storedPassword == usuarioPass.text.toString()) {
                            // Guardar el nombre de usuario en SharedPreferences
                            if(storedUser!=null && id!=null){
                                prefs.setName(storedUser)
                                prefs.setidUsuario(id)
                            }


                            // Contraseña coincide, redirige a homeApp
                            val intent = Intent(this, homeApp::class.java)
                            startActivity(intent)
                            return@addOnSuccessListener
                        }
                    }
                    // Si no se encuentra un usuario coincidente, muestra un mensaje de error
                    Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                }
        }

        val buttonClickRegister = findViewById<Button>(R.id.button3)
        buttonClickRegister.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }
    }

    private fun setupDrawer() {
        drawerLayout = findViewById(R.id.myDrawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // Inicializar ActionBarDrawerToggle
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Mostrar el ícono de navegación en la barra de aplicaciones
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Listener para el ícono de navegación en la barra de aplicaciones
        actionBarDrawerToggle.setToolbarNavigationClickListener {
            Toast.makeText(this, "Icono de navegación clicado", Toast.LENGTH_SHORT).show()
        }

        // Listener para los elementos del menú de navegación
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aboutUs -> {
                // Abrir la actividad aboutUs cuando se selecciona "About Us"
                val intent = Intent(this, aboutUs::class.java)
                startActivity(intent)
            }
        }

        return true
    }

    private fun checkUserValues() {

        if (prefs.getName().isNotEmpty()) {
            val intent = Intent(this, homeApp::class.java)
            startActivity(intent)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
