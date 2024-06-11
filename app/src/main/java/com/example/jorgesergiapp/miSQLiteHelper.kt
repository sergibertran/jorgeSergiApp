package com.example.jorgesergiapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MiSQLiteHelper(context: Context) : SQLiteOpenHelper(
    context, "practica.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE pokemons " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT, descripcion TEXT, imagen TEXT)"
        db!!.execSQL(ordenCreacion)

        val ordenCreacion1 = "CREATE TABLE usuario " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT, password TEXT)"
        db.execSQL(ordenCreacion1)

    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS pokemons"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun anyadirDato(nombre: String, descripcion: String, imagen: String) {
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email", descripcion)
        datos.put("imagen", descripcion)

        val db = this.writableDatabase
        db.insert("pokemons", null, datos)
        db.close()
    }


}