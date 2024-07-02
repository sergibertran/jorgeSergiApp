package com.example.jorgesergiapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jorgesergiapp.models.Usuario
import com.example.jorgesergiapp.models.todosPokemons
import com.example.jorgesergiapp.userApp.Companion.prefs
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class profile : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    var minutos = 0L

    private val db = FirebaseFirestore.getInstance()
    private var pokemonsPropios: MutableList<String?> = mutableListOf()
    private var pokemons: MutableList<todosPokemons> = mutableListOf()
    private var nuevospokemons: MutableList<todosPokemons> = mutableListOf()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Show the notification.
            showNotification(this)
        } else {
            // Permission is denied. Handle accordingly.
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val timestampActual = Timestamp.now()

        // Convertir Timestamp a String
        val timestampString = timestampToString(timestampActual)

        // Comparar las fechas
        val comparisonResult = isOlderThanThreeMinutes(prefs.getultimoOpening(), timestampString)


        if(comparisonResult && !prefs.getflipEnabled()){

        createNotificationChannel(this)
        // Check and request notification permission if needed
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted, show the notification
                showNotification(this)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                // Show rationale and request permission
                // You might want to show a dialog here explaining why the app needs this permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            else -> {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        }

        val fotoUsuario: ImageView = findViewById(R.id.imageView3)


        fotoUsuario.setBackgroundResource(R.drawable.profile_button)

       // val pokemons = listOf(
       ////     detalle.pokemon("Raichu", "Descripción 2", R.drawable.pokemon_17),
        ////)


        val buttonClickModifUser = findViewById<Button>(R.id.button4)
        buttonClickModifUser.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").whereEqualTo("usuario", prefs.getName())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {

                        val userList = Usuario(
                            usuario = document.getString("usuario"),
                            password = document.getString("password"),
                            foto = document.getString("foto"),
                            email = document.getString("email"),
                            tipoUsuario = document.getString("tipoUsuario")
                        )

                        val intent = Intent(this, editFormNormalUser::class.java).apply {
                            putExtra("usuario", userList)
                        }
                        startActivity(intent)
                    }

                }


        }


        val buttonLogoUT = findViewById<Button>(R.id.button5)
        buttonLogoUT.setOnClickListener {

            prefs.wipe()

            val intent = Intent(this, loginApp::class.java)
            startActivity(intent)
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
                            }
                        }
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCaja)
                        recyclerView.layoutManager = GridLayoutManager(this, 2)

                        val adapter = PokemonAdapter(nuevospokemons)

                        recyclerView.adapter = adapter



                        val pokemonAdapter = PokemonAdapter(nuevospokemons)
                        recyclerView.adapter = pokemonAdapter

                    }


            }









    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun showNotification(context: Context) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, loginApp::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.aklogo_1)
            .setContentTitle("App Pokemon")
            .setContentText("Te quedan "+minutos+ " segundos para poder volver a jugar")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // Check for notification permission
            if (ActivityCompat.checkSelfPermission(
                    context, // Use context here
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Request permission here if needed
                return
            }
            // NotificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }


        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected.
                // Use a dialog or a snackbar to show the explanation.
            }
            else -> {
                // Directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    // Función para convertir Timestamp a String
    fun timestampToString(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

    // Función para convertir String a Date
    fun stringToDate(dateString: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            sdf.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Función para comparar dos fechas en formato String
    fun isOlderThanThreeMinutes(dateString1: String, dateString2: String): Boolean {
        val date1 = stringToDate(dateString1)
        val date2 = stringToDate(dateString2)

        if (date1 != null && date2 != null) {
            val diffInMillis = date2.time - date1.time
            var diff = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

            if(diffInMinutes < 3){

                minutos= 60*3-diff
                return true
            }
        } else {
            null
        }
        return false
    }



}