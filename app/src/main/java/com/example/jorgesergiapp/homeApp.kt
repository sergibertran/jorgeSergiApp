package com.example.jorgesergiapp


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.media.MediaPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView


class homeApp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var mediaPlayer: MediaPlayer? = null
        var isPlaying = false
        var isAdmin: Boolean = false
        lateinit var front_animation:AnimatorSet
        lateinit var back_animation: AnimatorSet
        var isFront =true



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        isAdmin = checkIfUserIsAdmin()
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        val menuResId = if (isAdmin) R.menu.bottom_nav_menu_admin else R.menu.bottom_nav_menu

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


        val cardFront: TextView = findViewById(R.id.card_front)
        val cardBack: TextView = findViewById(R.id.card_back)


        cardFront.setBackgroundResource(R.drawable.card_random)
        cardBack.setBackgroundResource(R.drawable.pokemon_6)


        var scale = applicationContext.resources.displayMetrics.density
        val front = findViewById<TextView>(R.id.card_front) as TextView
        val back =findViewById<TextView>(R.id.card_back) as TextView
        val flip = findViewById<Button>(R.id.flip_btn) as Button
        val sound = findViewById<Button>(R.id.sound) as Button
        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale


        front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        flip.setOnClickListener{
            if(isFront)
            {
                front_animation.setTarget(front);
                back_animation.setTarget(back);
                front_animation.start()
                back_animation.start()
                isFront = false
                flip.isEnabled=false
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

    private fun checkIfUserIsAdmin(): Boolean {
        val usuario = loginApp.UserManager.usuario
        if (usuario != null) {

            if (usuario.usuario == "admin") {
                return true;

            }


        }
        return false;
    }
}
