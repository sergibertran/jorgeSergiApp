package com.example.jorgesergiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.pokemons
import com.example.jorgesergiapp.models.todosPokemons
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class detalle : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokemons: MutableList<todosPokemons> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val db = FirebaseFirestore.getInstance()
        db.collection("todosPokemons")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val pokemon = todosPokemons(id,document.getString("descripcion"),document.getString("nombre"),document.getString("foto"))
                    pokemons.add(pokemon)



                }

                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                val adapter = PokemonAdapter(pokemons)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter



                val pokemonAdapter = PokemonAdapter(pokemons)
                recyclerView.adapter = pokemonAdapter
            }



        //val pokemons = listOf(
        //    pokemon("Pikachu", "Descripción 1", R.drawable.pokemon_6),
        //    pokemon("Raichu", "Descripción 2", R.drawable.pokemon_17),
        //    // Agrega más usuarios según sea necesario
        //)




    }




}