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
import com.google.firebase.firestore.FirebaseFirestore

class editFormUser : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_form_user)

        val user: Usuario? = intent.getParcelableExtra("usuario", Usuario::class.java)
        if (user != null) {
            val usuario: TextView =   findViewById<TextView>(R.id.editTextUsuario)
            val email: TextView =   findViewById<TextView>(R.id.editTextEmail)
            val foto: TextView =   findViewById<TextView>(R.id.editTextFoto)
            val password: TextView =   findViewById<TextView>(R.id.editTextPassword5)
            val switch2 = findViewById<Switch>(R.id.switch2)

            switch2.isChecked = user.tipoUsuario == "1"
            usuario.text=user.usuario
            email.text=user.email
            foto.text=user.foto
            password.text=user.password

            val buttonClickGuardar =  findViewById<Button>(R.id.button6)
            buttonClickGuardar.setOnClickListener {
            // guardar

                }
            val buttonClickEliminar =  findViewById<Button>(R.id.button6)
            buttonClickEliminar.setOnClickListener {
            // eliminar



            }

        }


    }

    }




