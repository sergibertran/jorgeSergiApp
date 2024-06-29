package com.example.jorgesergiapp

import com.example.jorgesergiapp.models.obtenerCartas
import com.example.jorgesergiapp.models.pokemons
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
class firestoreHelper {

    private val db = FirebaseFirestore.getInstance()

    fun obtenerIdUsuarioPorNombre(
        nombreEntrenador: String,
        onSuccess: (Number) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("Entrenador")
            .whereEqualTo("nombre", nombreEntrenador)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onFailure(Exception("Entrenador no encontrado"))
                } else {
                    for (document in documents) {
                        val idUsuario = document.get("idUsuario") as? Number
                        if (idUsuario != null) {
                            onSuccess(idUsuario)
                        } else {
                            onFailure(Exception("ID de usuario no encontrado"))
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun obtenerCartas(
        idUsuario: String,
        onSuccess: (List<obtenerCartas>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()

        db.collection("obtenerCarta")
            .whereEqualTo("idUsuario", idUsuario)
            .get()
            .addOnSuccessListener { documents ->
                val cartas = mutableListOf<obtenerCartas>()
                for (document in documents) {
                    val idUsuario = document.get("idUsuario") as String
                    val ultimoOpeningTimestamp = document.get("ultimoOpening") as Timestamp

                    val carta = obtenerCartas(idUsuario, ultimoOpeningTimestamp)
                    cartas.add(carta)
                }
                onSuccess(cartas)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
        fun obtenerPokemonsUsuarios(
            idUsuario: String,
            onSuccess: (List<pokemons>) -> Unit,
            onFailure: (Exception) -> Unit
        ) {
            val db = FirebaseFirestore.getInstance()

            db.collection("pokemonsUsuarios")
                .whereEqualTo("idUsuario", idUsuario)
                .get()
                .addOnSuccessListener { documents ->
                    val pokemons = mutableListOf<pokemons>()
                    for (document in documents) {
                        val idUsuario = document.get("idUsuario") as String
                        val idPokemon = document.get("idPokemon") as String
                        val pokemon = pokemons(idUsuario, idPokemon)
                        pokemons.add(pokemon)
                    }
                    onSuccess(pokemons)
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    // Función para verificar si el documento existe


    // Función para actualizar el campo "ultimoOpening" si el documento ya existe
    fun actualizarUltimoOpening(idUsuario: String, timestampActual: Timestamp) {
        val db = FirebaseFirestore.getInstance()
        val obtenerCartasRef = db.collection("obtenerCarta")

        obtenerCartasRef.whereEqualTo("idUsuario", idUsuario).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    println("No se encontró ningún documento para el usuario $idUsuario en la colección 'obtenerCarta'.")
                } else {
                    for (document in documents) {
                        val documentRef = document.reference
                        documentRef.update("ultimoOpening", timestampActual)
                            .addOnSuccessListener {
                                println("Se actualizó 'ultimoOpening' para el usuario $idUsuario.")
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

    // Función para insertar un nuevo documento si no existe
    fun insertarNuevoDocumento( idUsuario: String, timestampActual: Timestamp) {
        val db = FirebaseFirestore.getInstance()
        val nuevaObtenerCarta = obtenerCartas(idUsuario, timestampActual)

        val obtenerCartasRef = db.collection("obtenerCartas").document(idUsuario)

        obtenerCartasRef.set(nuevaObtenerCarta)
            .addOnSuccessListener {
                println("Se insertó un nuevo documento para el usuario $idUsuario.")
            }
            .addOnFailureListener { exception ->
                println("Error al insertar nuevo documento: $exception")
            }
    }



}