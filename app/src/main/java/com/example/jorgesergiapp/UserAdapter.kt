package com.example.jorgesergiapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.jorgesergiapp.models.Usuario

class UserListAdapter(private val listaUsuarios: List<Usuario>) :
    RecyclerView.Adapter<UserListAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        val usuario: TextView = itemView.findViewById(R.id.textViewNombre)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsuarioViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {

        val usuario = listaUsuarios[position]
        val imageUrl = usuario.foto


        val imageViewFoto: ImageView = holder.imageViewFoto
        val textViewNombre: TextView =  holder.usuario

        imageViewFoto.load(imageUrl) {
            crossfade(true) // Animación de fade durante la carga de la imagen
        }

        textViewNombre.text = usuario.usuario


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, crud::class.java).apply {

            }
            context.startActivity(intent)
        }
    }






    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
