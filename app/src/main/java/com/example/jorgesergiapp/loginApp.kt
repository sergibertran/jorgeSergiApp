package com.example.jorgesergiapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.jorgesergiapp.models.Usuario

class home : AppCompatActivity() {
    object UserManager {
        var usuario: Usuario? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        main();
        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val usuarioEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
            val usuarioObjeto = Usuario(usuarioEditText.text.toString(), "",0,0)
            // Guardar información del usuario en el Singleton
            UserManager.usuario = usuarioObjeto

            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val buttonClickRegister = findViewById<Button>(R.id.button3)
        buttonClickRegister.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }



    }







    fun main() {

    }


}