package com.example.jorgesergiapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.UsuarioList
import com.example.jorgesergiapp.userApp.Companion.prefs
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class editFormUser : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_form_user)
        var isAdmin: String = "0"
        var userExists: Boolean = false
        val buttonClickGuardar =  findViewById<Button>(R.id.button6)
        val buttonClickEliminar =  findViewById<Button>(R.id.button8)
        val usuario: TextView =   findViewById<TextView>(R.id.editTextUsuario)
        val email: TextView =   findViewById<TextView>(R.id.editTextEmail)
        val foto: TextView =   findViewById<TextView>(R.id.editTextFoto)
        val password: TextView =   findViewById<TextView>(R.id.editTextPassword5)
        val switch2 = findViewById<Switch>(R.id.switch2)
        var documentId: String


        var usuarioNombre: String? = ""
        val user: Usuario? = intent.getParcelableExtra("usuario", Usuario::class.java)
        if (user != null) {

            userExists=true
            switch2.isChecked = user.tipoUsuario == "1"
            usuario.text=user.usuario
            email.text=user.email
            foto.text=user.foto
            password.text=user.password




        }else{
            buttonClickEliminar.isEnabled=false
        }
        buttonClickGuardar.setOnClickListener {
            if(!usuario.text.isEmpty() && !email.text.isEmpty() && !foto.text.isEmpty() && !password.text.isEmpty()){


                val db = FirebaseFirestore.getInstance()
                db.collection("usuarios").whereEqualTo("usuario", usuario.text.toString())
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            usuarioNombre = (document.getString("usuario"))


                        }

                        if(switch2.isChecked){
                            isAdmin="1"
                        }else{
                            isAdmin="0"
                        }
                        val user = Usuario(usuario.text.toString(), password.text.toString(),foto.text.toString(), email.text.toString(),isAdmin)

                        if(!userExists){
                            if(!usuarioNombre.equals(usuario.text.toString())){

                                val db = FirebaseFirestore.getInstance()

                                db.collection("usuarios")
                                    .add(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                        finish()
                                        val intent = Intent(this, loginApp::class.java)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }else{
                                Toast.makeText(this, "Ya existe este nobmre de usuario: ", Toast.LENGTH_SHORT).show()
                            }
                        }else{


                            val db = FirebaseFirestore.getInstance()
                            val obtenerCartasRef = db.collection("usuarios")
                            obtenerCartasRef.whereEqualTo("usuario", usuarioNombre).get()
                                .addOnSuccessListener { documents ->
                                    if (documents.isEmpty) {
                                        println("No se encontró ningún documento para el usuario $usuario.text.toString() en la colección 'obtenerCarta'.")
                                    } else {
                                        for (document in documents) {
                                            val documentRef = document.reference
                                            documentRef.update("email", email.text.toString())
                                            documentRef.update("foto", foto.text.toString())
                                            documentRef.update("password", password.text.toString())
                                            documentRef.update("tipoUsuario", isAdmin)
                                            documentRef.update("usuario", usuario.text.toString())
                                                .addOnSuccessListener {
                                                    Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                    val intent = Intent(this, userDetalle::class.java)
                                                    startActivity(intent)
                                                }
                                                .addOnFailureListener { exception ->
                                                    println("Error al actualizar 'ultimoOpening': $exception")
                                                }
                                        }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    println("Error al realizar la consulta: $exception")
                                }




                        }

                    }



                    }

        }

        buttonClickEliminar.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val obtenerCartasRef = db.collection("usuarios")
            obtenerCartasRef.whereEqualTo("usuario", usuario.text.toString()).get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        println("No se encontró ningún documento para el usuario ${usuario.text.toString()} en la colección 'usuarios'.")
                    } else {
                        for (document in documents) {
                            documentId = document.id
                            var documentRef = document.reference
                            documentRef.delete()
                                .addOnSuccessListener {
                                    println("Se eliminó el documento para el usuario ${usuario.text}.")


                                    val obtenerPokemonsRef = db.collection("pokemonsUsuarios")
                                    obtenerPokemonsRef.whereEqualTo("idUsuario", documentId).get()
                                        .addOnSuccessListener { pokemonDocuments ->
                                            if (pokemonDocuments.isEmpty) {
                                                println("No se encontró ningún documento en la colección 'pokemonsUsuarios' para el usuario ${usuario.text.toString()}.")
                                            } else {
                                                for (pokemonDocument in pokemonDocuments) {
                                                    val pokemonDocumentRef = pokemonDocument.reference
                                                    pokemonDocumentRef.delete()
                                                        .addOnSuccessListener {
                                                            println("Se eliminó el documento en 'pokemonsUsuarios' para el usuario ${usuario.text}.")


                                                            val obtenerPokemonsRef = db.collection("obtenerCartas")
                                                            obtenerPokemonsRef.whereEqualTo("idUsuario", documentId).get()
                                                                .addOnSuccessListener { pokemonDocuments ->
                                                                    if (pokemonDocuments.isEmpty) {
                                                                        println("No se encontró ningún documento en la colección 'pokemonsUsuarios' para el usuario ${usuario.text.toString()}.")
                                                                    } else {
                                                                        for (pokemonDocument in pokemonDocuments) {
                                                                            val pokemonDocumentRef = pokemonDocument.reference
                                                                            pokemonDocumentRef.delete()
                                                                                .addOnSuccessListener {
                                                                                    println("Se eliminó el documento en 'pokemonsUsuarios' para el usuario ${usuario.text}.")
                                                                                    Toast.makeText(this, "Todo eliminado correctamente", Toast.LENGTH_SHORT).show()

                                                                                }
                                                                                .addOnFailureListener { exception ->
                                                                                    println("Error al eliminar el documento en 'pokemonsUsuarios': $exception")
                                                                                }
                                                                        }
                                                                    }
                                                                }



                                                        }
                                                        .addOnFailureListener { exception ->
                                                            println("Error al eliminar el documento en 'pokemonsUsuarios': $exception")
                                                        }
                                                }
                                            }
                                        }

                                    if(usuario.text.toString().equals(prefs.getName())){
                                        finish()

                                        val intent = Intent(this, loginApp::class.java)
                                        startActivity(intent)
                                        prefs.wipe()

                                    }else{
                                        finish()
                                        val intent = Intent(this, userDetalle::class.java)
                                        startActivity(intent)
                                    }


                                }
                                .addOnFailureListener { exception ->
                                    println("Error al eliminar el documento: $exception")
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error al realizar la consulta: $exception")
                }




        }

    }

}