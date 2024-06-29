package com.example.jorgesergiapp


import com.google.firebase.Timestamp
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.graphics.drawable.Drawable


import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jorgesergiapp.models.obtenerCartas
import com.example.jorgesergiapp.models.pokemons
import com.example.jorgesergiapp.models.todosPokemons
import com.example.jorgesergiapp.userApp.Companion.prefs
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random


class homeApp : AppCompatActivity() {

    private var pokemonsPropios: MutableList<String?> = mutableListOf()
    private var pokemons: MutableList<todosPokemons> = mutableListOf()
    private var nuevospokemons: MutableList<todosPokemons> = mutableListOf()
    private var pokemonsDisponibles: MutableList<todosPokemons> = mutableListOf()
    private lateinit var pokemonSorpresa: todosPokemons
    private lateinit var pokemonSave: pokemons
    override fun onCreate(savedInstanceState: Bundle?) {

        var mediaPlayer: MediaPlayer? = null
        var isPlaying = false

        lateinit var front_animation:AnimatorSet
        lateinit var back_animation: AnimatorSet
        var isFront =true

        var obtenerCartas = mutableListOf<obtenerCartas>()
        val firestoreHelper = firestoreHelper()
        var terminado=false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)






        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        val menuResId = if (prefs.getName().equals("admin")) R.menu.bottom_nav_menu_admin else R.menu.bottom_nav_menu

        // Establecer el menú en el BottomNavigationView
        navigationView.menu.clear() // Limpiar el menú actual
        navigationView.inflateMenu(menuResId) // Inflar el menú correspondiente

        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, homeApp::class.java)
                    startActivity(intent)
                    true
                } R.id.profile -> {
                val intent = Intent(this, profile::class.java)
                startActivity(intent)
                true
                } R.id.listaTodosPokemons -> {
                val intent = Intent(this, detalle::class.java)
                startActivity(intent)
                true
                }
                // Agrega más casos según sea necesario
                else -> false
            }
        }
        val db = FirebaseFirestore.getInstance()
        db.collection("todosPokemons")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.id
                    val pokemon = todosPokemons(id,document.getString("descripcion"),document.getString("nombre"),document.getString("foto"))
                    pokemons.add(pokemon)

                }
                db.collection("pokemonsUsuarios")
            .whereEqualTo("idUsuario", prefs.getidUsuario())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    pokemonsPropios.add(document.getString("idPokemon"))

                }
                for (pokemon in pokemons) {
                    // Verificar si el ID del pokemon está en pokemonsPropios
                    if (pokemonsPropios.contains(pokemon.id)) {
                        // Si sí está, agregar el pokemon a la lista nuevospokemons
                        nuevospokemons.add(pokemon)
                    } else {
                        // Si no está, agregar el pokemon a la lista pokemonsDisponibles
                        pokemonsDisponibles.add(pokemon)
                    }
                }
                val cardFront: TextView = findViewById(R.id.card_front)
                val cardBack: TextView = findViewById(R.id.card_back)


                cardFront.setBackgroundResource(R.drawable.card_random)
                if (pokemonsDisponibles.isNotEmpty()) {
                    val imageUrl: String?
                    val cardBack: TextView = findViewById(R.id.card_back)
                    if (pokemonsDisponibles.size == 1) {
                        // Si solo hay un Pokémon disponible, seleccionarlo
                        pokemonSorpresa = pokemonsDisponibles[0]

                        imageUrl = pokemonSorpresa.foto
                    } else {
                        // Si hay más de un Pokémon disponible, seleccionar uno al azar
                        val index = Random.nextInt(pokemonsDisponibles.size)
                        pokemonSorpresa = pokemonsDisponibles[index]


                        imageUrl = pokemonSorpresa.foto



                    }
                    pokemonSave = pokemons(prefs.getidUsuario(), pokemonSorpresa.id)


                    Thread {
                        try {
                            val inputStream = URL(imageUrl).openStream()
                            val bitmap = BitmapFactory.decodeStream(inputStream)

                            runOnUiThread {
                                val drawable = BitmapDrawable(resources, bitmap)
                                cardBack.background = drawable
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }.start()
                }
            }
            }

        var scale = applicationContext.resources.displayMetrics.density
        val front = findViewById<TextView>(R.id.card_front) as TextView
        val back =findViewById<TextView>(R.id.card_back) as TextView
        val flip = findViewById<Button>(R.id.flip_btn) as Button
        val sound = findViewById<Button>(R.id.sound) as Button
        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale


        front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet




        val idUsuario = prefs.getidUsuario()
        val timestampActual = Timestamp.now()
        firestoreHelper.obtenerCartas(idUsuario,
            onSuccess = { cartas ->
                obtenerCartas =cartas.toMutableList()

                if(obtenerCartas.size>0 && ((timestampActual.seconds - obtenerCartas.get(0).ultimoOpening.seconds  ) / 60) > 3){
                    flip.isEnabled=true
                    prefs.setflipEnabled(true)
                    val date = timestampActual.toDate()
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    prefs.setultimoOpening(sdf.format(date))
                }else{
                    prefs.setflipEnabled(false)
                    flip.isEnabled=false
                    val date = obtenerCartas.get(0).ultimoOpening.toDate()
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    prefs.setultimoOpening(sdf.format(date))



                }
                terminado=true;
            },
            onFailure = { exception ->
                Toast.makeText(this, "Error al obtener cartas: ${exception.message}", Toast.LENGTH_SHORT).show()
                terminado=true;
                val date = timestampActual.toDate()
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                prefs.setultimoOpening(sdf.format(date))
            }
        )



        flip.setOnClickListener{
            if(isFront)
            {


                if(terminado){



                    db.collection("pokemonsUsuarios")
                        .add(pokemonSave)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            // Puedes redirigir al usuario a otra pantalla si lo deseas
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }



                    val timestampActual = Timestamp.now()
                    front_animation.setTarget(front);
                    back_animation.setTarget(back);
                    front_animation.start()
                    back_animation.start()
                    isFront = false
                    flip.isEnabled=false
                    prefs.setflipEnabled(false)

                // Realizar la operación según el valor de documentoExiste
                if (obtenerCartas.size>0 ) {
                    // El documento existe, actualizar el campo "ultimoOpening"
                    firestoreHelper.actualizarUltimoOpening(idUsuario, timestampActual)

                } else {
                    // El documento no existe, insertar uno nuevo
                    firestoreHelper.insertarNuevoDocumento(idUsuario.toString(), timestampActual)
                }

                }

            }
            else
            {
                front_animation.setTarget(back)
                back_animation.setTarget(front)
                back_animation.start()
                front_animation.start()
                isFront =true


            }
        }
        sound.setOnClickListener {
            if (isPlaying) {
                // Detener la reproducción
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0) // Opcional: para reiniciar desde el principio
                isPlaying = false
                sound.text = "Musica"
            } else {
                // Iniciar la reproducción
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.musica) // Ajusta aquí el nombre del archivo
                    mediaPlayer?.setOnCompletionListener {
                        // Restablecer cuando la música termina
                        isPlaying = false
                        sound.text = "Musica"
                    }
                }
                mediaPlayer?.start()
                isPlaying = true
                sound.text = "Parar"
            }
        }











    }
    }





