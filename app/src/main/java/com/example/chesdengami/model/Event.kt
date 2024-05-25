package com.example.chesdengami.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "event_name") val eventName: String,
//    @ColumnInfo(name = "member_list") val memberList: List<Member> //toask так как есть класс с one to many EventWithMembers
)
//todo потом добавить дату