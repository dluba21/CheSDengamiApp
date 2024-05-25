package com.example.chesdengami.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chesdengami.model.Member

@Dao
interface MemberDao {
    @Query("select * from member_table m where m.event_id = :eventId")
    fun getMembersByEventId(eventId: Long): LiveData<List<Member>>

    @Insert
    suspend fun addMember(member: Member)
}