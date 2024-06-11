package com.example.jorgesergiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class detalle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val pokemons = listOf(
            pokemon("Pikachu", "Descripción 1", R.drawable.pokemon_6),
            pokemon("Raichu", "Descripción 2", R.drawable.pokemon_17),
            // Agrega más usuarios según sea necesario
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = UsuarioAdapter(pokemons)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

    data class pokemon(val nombre: String, val descripcion: String, val foto: Int)
}