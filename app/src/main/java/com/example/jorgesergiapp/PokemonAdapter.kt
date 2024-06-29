package com.example.jorgesergiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.todosPokemons
import coil.load
class PokemonAdapter(private val listaUsuarios: List<todosPokemons>) :
    RecyclerView.Adapter<PokemonAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return UsuarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        val imageUrl = usuario.foto


        val imageViewFoto: ImageView = holder.imageViewFoto
        imageViewFoto.load(imageUrl) {
            crossfade(true) // Animación de fade durante la carga de la imagen
        }

        holder.textViewNombre.text = usuario.nombre
        holder.textViewDescripcion.text = usuario.descripcion
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
