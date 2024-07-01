package com.example.jorgesergiapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList
import com.example.jorgesergiapp.models.todosPokemons
import com.google.firebase.firestore.FirebaseFirestore

class editFormPokemon : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_form_pokemon)

        val buttonClickGuardar =  findViewById<Button>(R.id.button6)
        val buttonClickEliminar =  findViewById<Button>(R.id.button8)

        val poke: todosPokemons? = intent.getParcelableExtra("pokemon", todosPokemons::class.java)
        if (poke != null) {


            val usuario: TextView =   findViewById<TextView>(R.id.editTextUsuario)
            val foto: TextView =   findViewById<TextView>(R.id.editTextFoto)
            val descripcion: TextView =   findViewById<TextView>(R.id.editTextEmail)

            foto.text=poke.foto
            usuario.text=poke.nombre
            descripcion.text=poke.descripcion




        }else{
            buttonClickEliminar.isEnabled=false
        }

        buttonClickGuardar.setOnClickListener {
            // guardar

        }

        buttonClickEliminar.setOnClickListener {
            // eliminar



        }

    }

    }




