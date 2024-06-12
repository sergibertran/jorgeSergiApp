package com.example.jorgesergiapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario

class UserListAdapter(private val listaUsuarios: List<Usuario>) :
    RecyclerView.Adapter<UserListAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFoto: ImageView = itemView.findViewById(R.id.imageViewFoto)
        val usuario: TextView = itemView.findViewById(R.id.user)


        fun bind(usuario: Usuario) {

            this.usuario.text = usuario.usuario
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UsuarioViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        // Obtener el usuario en la posición actual
        val usuario = listaUsuarios[position]

        // Llamar al método bind del ViewHolder para asociar los datos del usuario con las vistas
        holder.bind(usuario)



        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, crud::class.java).apply {
                // Agregar el usuario como extra al Intent
                putExtra("usuario", usuario)
            }
            context.startActivity(intent)
        }
    }






    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}
