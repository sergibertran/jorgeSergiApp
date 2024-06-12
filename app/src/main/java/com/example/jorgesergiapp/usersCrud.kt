package com.example.jorgesergiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario

class usersCrud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

            val usuarios: List<Usuario> = listOf(
                Usuario("admin", "password1", foto = obtenerRecursoImagen(1),tipoUsuario = 0),
                Usuario("usuario2", "password2",foto = obtenerRecursoImagen(0),tipoUsuario = 0),
                Usuario("usuario3", "password3",foto = obtenerRecursoImagen(1),tipoUsuario = 1)

            )



        val recyclerView: RecyclerView = findViewById(R.id.recyclerUserView)
        val adapter = UserListAdapter(usuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter





    }

    private fun obtenerRecursoImagen(i: Int): Int {
        if (i == 1){
            return R.drawable.baseline_admin_panel_settings_24;
        }else if (i == 0){
            return R.drawable.baseline_supervised_user_circle_24;
        }
        return R.drawable.baseline_supervised_user_circle_24;
    }
}