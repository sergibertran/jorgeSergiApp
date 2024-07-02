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

class UserAdapter(private val listaUsuarios: List<UsuarioList>) :
    RecyclerView.Adapter<UserAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        val usuario: TextView = itemView.findViewById(R.id.textViewName)
        val buttonClickRegister: Button = itemView.findViewById(R.id.button7)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsuarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]

        val imageUrl = usuario.foto

        holder.imageViewFoto.load(imageUrl) {
            crossfade(true) // Animación de fade durante la carga de la imagen
        }

        holder.usuario.text = usuario.usuario

        holder.buttonClickRegister.setOnClickListener {
            val userList = Usuario(
                usuario = usuario.usuario,
                password = usuario.password,
                foto = usuario.foto,
                email = usuario.email,
                tipoUsuario = usuario.tipoUsuario
            )



            val intent = Intent(holder.itemView.context, editFormUser::class.java).apply {
                putExtra("usuario", userList)

            }

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
