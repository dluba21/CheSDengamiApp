package com.example.chesdengami.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chesdengami.model.Event
import com.example.chesdengami.model.Member
import com.example.chesdengami.model.Purchase
import com.example.chesdengami.tmp.PurchaseDao


@Database(entities = [Event::class, Member::class, Purchase::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun memberDao(): MemberDao
    abstract fun purchaseDao(): PurchaseDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null
        fun getDatabase(
            context: Context
        ): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_room_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}