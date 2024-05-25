package com.example.chesdengami.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Room
import kotlin.reflect.KClass

@Entity(tableName = "member_table",
     foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["event_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Member(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "member_name") val memberName: String,
    @ColumnInfo("event_id") val eventId: Long // Указано, что eventId - это внешний ключ
)