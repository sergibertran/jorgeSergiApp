package com.example.jorgesergiapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView


class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var isAdmin: Boolean = false


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
        val intent = Intent(context, login::class.java)
        // Iniciar la nueva actividad
        context.startActivity(intent)
    }
}

    private fun checkIfUserIsAdmin(): Boolean {
        val usuario = home.UserManager.usuario
        if (usuario != null) {

            if (usuario.usuario == "admin") {
                return true;

            }


        }
        return false;
    }
}
