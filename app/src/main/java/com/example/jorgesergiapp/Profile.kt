package com.example.jorgesergiapp

import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class Profile : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        var isAdmin: Boolean = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)


        // Replace "your_user_id" with the actual user ID (if you have authentication)
        val userId = "your_user_id"

        // Retrieve the document from the "Entrenador" collection
        db.collection("Entrenador")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    // Get the values from the document
                    val nombre = document.getString("Nombre")
                    val correo = document.getString("correo")

                    // Display the information in the TextViews

                    nameTextView.text = "Nombre: $nombre"
                    emailTextView.text = "Correo: $correo"
                } else {
                    // Document doesn't exist
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        val menuResId = if (isAdmin) R.menu.bottom_nav_menu_admin else R.menu.bottom_nav_menu

        // Establecer el menú en el BottomNavigationView
        navigationView.menu.clear() // Limpiar el menú actual
        navigationView.inflateMenu(menuResId) // Inflar el menú correspondiente
        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, detalle::class.java)
                    startActivity(intent)
                    true
                }
                R.id.page_4 -> {
                    val intent = Intent(this, usersCrud::class.java)
                    startActivity(intent)
                    true
                }R.id.profile -> {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
                true
            }
                // Agrega más casos según sea necesario
                else -> false
            }
        }
    }
}