package com.example.jorgesergiapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario

class crud : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)






        // Recuperar el objeto de usuario del Intent
        val usuario: Usuario? =  intent.getParcelableExtra("DATA", Usuario::class.java)

        // Verificar si el objeto de usuario no es nulo
        if (usuario != null) {
            // Ahora puedes utilizar el objeto de usuario como lo necesites
            // Por ejemplo, puedes mostrar el nombre del usuario en un TextView
            val nombreTextView: TextView = findViewById(R.id.TextUser)
            nombreTextView.text = usuario.usuario
        }






    }


}