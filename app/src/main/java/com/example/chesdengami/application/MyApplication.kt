package com.example.chesdengami.application

import android.app.Application
import android.util.Log
import com.example.chesdengami.data.AppRepository
import com.example.chesdengami.data.AppRoomDatabase

class MyApplication : Application() {
    private val database by lazy { AppRoomDatabase.getDatabase(this) }
    val appRepository by lazy { AppRepository(database.eventDao(), database.memberDao(), database.purchaseDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}