package com.example.jorgesergiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.pokemons
import com.example.jorgesergiapp.models.todosPokemons
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class users : AppCompatActivity() {


    private var users: MutableList<Usuario> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val user = Usuario(id,document.getString("password"),document.getString("foto"),document.getString("email"),document.getString("tipoUsuario"))
                    users.add(user)

                }

                val recyclerView: RecyclerView = findViewById(R.id.recyclerUserView)
                val adapter = UserListAdapter(users)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

            }





    }




}