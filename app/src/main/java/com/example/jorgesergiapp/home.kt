package com.example.jorgesergiapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val db = MiSQLiteHelper(this)
        db.writableDatabase
        val navigationView =  findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    val intent = Intent(this, detalle::class.java)
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
        val intent = Intent(context, home::class.java)
        // Iniciar la nueva actividad
        context.startActivity(intent)
    }
}
}
