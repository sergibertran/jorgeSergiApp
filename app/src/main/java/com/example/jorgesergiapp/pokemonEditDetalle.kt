package com.example.jorgesergiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList
import com.example.jorgesergiapp.models.todosPokemons
import com.google.firebase.firestore.FirebaseFirestore

class pokemonEditDetalle : AppCompatActivity() {


    private var pokemonsList: MutableList<todosPokemons> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemonedit_detalle)



        val db = FirebaseFirestore.getInstance()
        db.collection("todosPokemons")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val pokemons = todosPokemons(id,document.getString("descripcion"),document.getString("nombre"),document.getString("foto"))
                    pokemonsList.add(pokemons)

                }

                val recyclerView: RecyclerView = findViewById(R.id.recyclerPokemonEditView)
                val adapter = pokemonEditAdapter(pokemonsList)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter



            }





    }






}