package com.example.jorgesergiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class detalle : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokemons: List<PokemonFB> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val pokemons = listOf(
            pokemon("Pikachu", "Descripción 1", R.drawable.pokemon_6),
            pokemon("Raichu", "Descripción 2", R.drawable.pokemon_17),
            // Agrega más usuarios según sea necesario
        )



        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = PokemonAdapter(pokemons)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        val pokemonAdapter = PokemonAdapter(pokemons)
        recyclerView.adapter = pokemonAdapter
        fetchPokemonsFromFirestore()
    }

    data class pokemon(val nombre: String, val descripcion: String, val foto: Int)
    data class PokemonFB(
        val Nombre: String = "asd",
        val Tipo: String = "asd",
        val Descripcion: String = "asd",
        val Foto: Int = 0  // Si necesitas una referencia a una imagen local
    )
    private fun fetchPokemonsFromFirestore() {
        val db = Firebase.firestore

        db.collection("Pokemon")
            .get()
            .addOnSuccessListener { result ->
                pokemons = result.documents.mapNotNull { document ->
                    document.toObject<PokemonFB>()
                }

                updateUIWithPokemons()
            }
            .addOnSuccessListener { result ->
                pokemons = result.documents.mapNotNull { document ->
                    document.toObject<PokemonFB>()
                }

                // Imprime los datos de pokemons en la consola
                pokemons.forEachIndexed { index, pokemon ->
                    Log.d("Pokemons", "Pokemon $index: Nombre: ${pokemon.Nombre}, Descripción: ${pokemon.Descripcion}, Tipo: ${pokemon.Tipo}")
                }

                updateUIWithPokemons()
            }
            .addOnFailureListener { exception ->
                // Manejar errores aquí
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }
    private fun updateUIWithPokemons() {
        // Aquí puedes actualizar la UI con la lista de Pokémon obtenida
        // Por ejemplo, podrías actualizar un RecyclerView
    }
}