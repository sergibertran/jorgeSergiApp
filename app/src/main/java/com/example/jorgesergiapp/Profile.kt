package com.example.jorgesergiapp

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }
}