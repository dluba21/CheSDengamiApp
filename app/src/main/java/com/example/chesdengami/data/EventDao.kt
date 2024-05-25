package com.example.chesdengami.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chesdengami.model.Event

@Dao
interface EventDao {
    @Query("select * from EVENT_TABLE")
    fun getEventList(): LiveData<List<Event>> //toask почему тут livedata? почему не flow? в чем разница?

    @Query("select * from event_table where id = :eventId")
    fun getEventById(eventId: Long): LiveData<Event>

    @Update
    suspend fun updateEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(event: Event)
    @Query("delete from event_table where id in (:eventIds)")
    suspend fun deleteEventsByIds(eventIds: List<Long>)
}