package com.example.jorgesergiapp

import android.app.Application

class userApp : Application() {
    companion object{
        lateinit var prefs:Prefs

    }
    override fun onCreate() {
        super.onCreate()
    prefs = Prefs(applicationContext)
    }
}