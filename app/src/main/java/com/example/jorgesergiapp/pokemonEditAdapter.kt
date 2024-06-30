package com.example.jorgesergiapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList
import com.example.jorgesergiapp.models.todosPokemons

class pokemonEditAdapter(private val listaPokemons: List<todosPokemons>) :
    RecyclerView.Adapter<pokemonEditAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        val usuario: TextView = itemView.findViewById(R.id.textViewName)
        val buttonClickRegister: Button = itemView.findViewById(R.id.button7)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon_edit, parent, false)
        return UsuarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val pokemon = listaPokemons[position]

        val imageUrl = pokemon.foto

        holder.imageViewFoto.load(imageUrl) {
            crossfade(true) // Animación de fade durante la carga de la imagen
        }

        holder.usuario.text = pokemon.nombre

        holder.buttonClickRegister.setOnClickListener {
            val pokemonList = todosPokemons(
                id = pokemon.id,
                descripcion = pokemon.descripcion,
                nombre = pokemon.nombre,
                foto = pokemon.foto,
            )

            val intent = Intent(holder.itemView.context, editFormPokemon::class.java).apply {
                putExtra("pokemon", pokemonList)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaPokemons.size
    }
}
