package com.example.jorgesergiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList
import com.google.firebase.firestore.FirebaseFirestore

class userDetalle : AppCompatActivity() {


    private var users: MutableList<UsuarioList> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detalle)



        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val user = UsuarioList(id,document.getString("usuario"),document.getString("password"),document.getString("foto"),document.getString("email"),document.getString("tipoUsuario"))
                    users.add(user)

                }

                val recyclerView: RecyclerView = findViewById(R.id.recyclerUserView)
                val adapter = UserAdapter(users)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter



            }

        val buttonClickRegister = findViewById<Button>(R.id.button9)
        buttonClickRegister.setOnClickListener {
            val intent = Intent(this, editFormUser::class.java)
            startActivity(intent)
        }



    }






}