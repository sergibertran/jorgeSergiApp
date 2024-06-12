package com.example.jorgesergiapp


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


class homeApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var isAdmin: Boolean = false
        lateinit var front_animation:AnimatorSet
        lateinit var back_animation: AnimatorSet
        var isFront =true

        isAdmin = checkIfUserIsAdmin()
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)

            val menuResId = if (isAdmin) R.menu.bottom_nav_menu_admin else R.menu.bottom_nav_menu

        // Establecer el menú en el BottomNavigationView
        navigationView.menu.clear() // Limpiar el menú actual
        navigationView.inflateMenu(menuResId) // Inflar el menú correspondiente


        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, detalle::class.java)
                    startActivity(intent)
                    true
                }
                R.id.page_4 -> {
                    val intent = Intent(this, usersCrud::class.java)
                    startActivity(intent)
                    true
                }
                // Agrega más casos según sea necesario
                else -> false
            }
        }



    fun sendMessage(view: View) {
        // Do something in response to button
    }
    fun login(context: Context, savedInstanceState: Bundle?, view: View) {

        // Crear una intención para cambiar a la actividad home
        val intent = Intent(context, homeApp::class.java)
        // Iniciar la nueva actividad
        context.startActivity(intent)
    }
        val cardFront: TextView = findViewById(R.id.card_front)


        cardFront.setBackgroundResource(R.drawable.card_random)

        val cardBack: TextView = findViewById(R.id.card_back)

        cardBack.setBackgroundResource(R.drawable.pokemon_6)


        var scale = applicationContext.resources.displayMetrics.density

        val front = findViewById<TextView>(R.id.card_front) as TextView
        val back =findViewById<TextView>(R.id.card_back) as TextView
        val flip = findViewById<Button>(R.id.flip_btn) as Button

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale


        // Now we will set the front animation
        front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        // Now we will set the event listener
        flip.setOnClickListener{
            if(isFront)
            {
                front_animation.setTarget(front);
                back_animation.setTarget(back);
                front_animation.start()
                back_animation.start()
                isFront = false

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
