package com.example.jorgesergiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.userApp.Companion.prefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class profile : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokemons: List<detalle.PokemonFB> = listOf()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val fotoUsuario: ImageView = findViewById(R.id.imageView3)


        fotoUsuario.setBackgroundResource(R.drawable.profile_button)

        val pokemons = listOf(
            detalle.pokemon("Pikachu", "Descripción 1", R.drawable.pokemon_6),
            detalle.pokemon("Raichu", "Descripción 2", R.drawable.pokemon_17),
            // Agrega más usuarios según sea necesario
        )



        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCaja)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val adapter = PokemonAdapter(pokemons)

        recyclerView.adapter = adapter



        val pokemonAdapter = PokemonAdapter(pokemons)
        recyclerView.adapter = pokemonAdapter

        val buttonClickRegister = findViewById<Button>(R.id.button5)
        buttonClickRegister.setOnClickListener {

            prefs.wipe()

            val intent = Intent(this, loginApp::class.java)
            startActivity(intent)
        }

    }
}