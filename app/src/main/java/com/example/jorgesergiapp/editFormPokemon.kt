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
import com.example.jorgesergiapp.models.todosPokemonObject
import com.example.jorgesergiapp.models.todosPokemons
import com.google.firebase.firestore.FirebaseFirestore

class editFormPokemon : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_form_pokemon)

        val buttonClickGuardar =  findViewById<Button>(R.id.button6)
        val buttonClickEliminar =  findViewById<Button>(R.id.button8)
        val usuario: TextView =   findViewById<TextView>(R.id.editTextUsuario)
        val foto: TextView =   findViewById<TextView>(R.id.editTextFoto)
        val descripcion: TextView =   findViewById<TextView>(R.id.editTextEmail)
        var documentId: String
        var pokemonExists: Boolean = false
        var usuarioNombre: String? = ""
        val poke: todosPokemons? = intent.getParcelableExtra("pokemon", todosPokemons::class.java)
        var pokeNombre: String? = ""

        if (poke != null) {

            pokeNombre=poke.nombre

            pokemonExists=true
            foto.text=poke.foto
            usuario.text=poke.nombre
            descripcion.text=poke.descripcion



        }else{
            buttonClickEliminar.isEnabled=false
        }

        buttonClickGuardar.setOnClickListener {

            if(!usuario.text.isEmpty() && !foto.text.isEmpty() && !descripcion.text.isEmpty()){


                val db = FirebaseFirestore.getInstance()
                db.collection("todosPokemons").whereEqualTo("nombre", usuario.text.toString())
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            usuarioNombre = document.getString("nombre")


                        }

                        val pokemon = todosPokemonObject(descripcion.text.toString(),usuario.text.toString(),foto.text.toString(), )
                        if(!pokemonExists){
                            if(!usuarioNombre.equals(usuario.text.toString())){

                                val db = FirebaseFirestore.getInstance()

                                db.collection("todosPokemons")
                                    .add(pokemon)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                        finish()
                                        val intent = Intent(this, pokemonEditDetalle::class.java)
                                        startActivity(intent)

                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }

                            }else{
                                Toast.makeText(this, "Existe ya este pokemon", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            val db = FirebaseFirestore.getInstance()
                            val obtenerCartasRef = db.collection("todosPokemons")
                            obtenerCartasRef.whereEqualTo("nombre", usuarioNombre).get()
                                .addOnSuccessListener { documents ->
                                    if (documents.isEmpty) {
                                        println("No se encontró ningún documento para el usuario $usuario.text.toString() en la colección 'obtenerCarta'.")
                                    } else {
                                        for (document in documents) {
                                            val documentRef = document.reference
                                            documentRef.update("nombre", usuario.text.toString())
                                            documentRef.update("foto", foto.text.toString())
                                            documentRef.update("descripcion", descripcion.text.toString())

                                                .addOnSuccessListener {
                                                    println("Se actualizó 'ultimoOpening' para el usuario $usuario.text.")
                                                    finish()
                                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                                    val intent = Intent(this, pokemonEditDetalle::class.java)
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
            val obtenerPokemonsRef = db.collection("todosPokemons")
            obtenerPokemonsRef.whereEqualTo("nombre", pokeNombre).get()
                .addOnSuccessListener { pokemonDocuments ->
                    if (pokemonDocuments.isEmpty) {
                        println("No se encontró ningún documento en la colección 'pokemonsUsuarios' para el usuario ${usuario.text.toString()}.")
                    } else {
                        for (pokemonDocument in pokemonDocuments) {
                            documentId = pokemonDocument.id
                            val pokemonDocumentRef = pokemonDocument.reference
                            pokemonDocumentRef.delete()
                                .addOnSuccessListener {
                                    println("Se eliminó el documento en 'pokemonsUsuarios' para el usuario ${usuario.text}.")
                                    Toast.makeText(this, "Pokemon eliminado", Toast.LENGTH_SHORT).show()



                                    val db = FirebaseFirestore.getInstance()
                                    val obtenerPokemonsRef = db.collection("pokemonsUsuarios")
                                    obtenerPokemonsRef.whereEqualTo("idPokemon", documentId).get()
                                        .addOnSuccessListener { pokemonDocuments ->
                                            if (pokemonDocuments.isEmpty) {
                                                println("No se encontró ningún documento en la colección 'pokemonsUsuarios' para el usuario ${usuario.text.toString()}.")
                                            } else {
                                                for (pokemonDocument in pokemonDocuments) {
                                                    val pokemonDocumentRef = pokemonDocument.reference
                                                    pokemonDocumentRef.delete()
                                                        .addOnSuccessListener {
                                                            println("Se eliminó el documento en 'pokemonsUsuarios' para el usuario ${usuario.text}.")
                                                            Toast.makeText(this, "Pokemons en caja eliminados", Toast.LENGTH_SHORT).show()

                                                        }
                                                        .addOnFailureListener { exception ->
                                                            println("Error al eliminar el documento en 'pokemonsUsuarios': $exception")
                                                        }
                                                }
                                            }
                                            finish()
                                            val intent = Intent(this, pokemonEditDetalle::class.java)
                                            startActivity(intent)
                                        }



                                }
                                .addOnFailureListener { exception ->
                                    println("Error al eliminar el documento en 'pokemonsUsuarios': $exception")
                                }
                        }


                    }
                }


        }




    }
}