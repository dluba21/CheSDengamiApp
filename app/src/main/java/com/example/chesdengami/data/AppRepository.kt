package com.example.chesdengami.data

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Delete
import com.example.chesdengami.model.Event
import com.example.chesdengami.model.Member
import com.example.chesdengami.tmp.PurchaseDao


class AppRepository(
    private val eventDao: EventDao, //todo можно даггер сделать инжект потом посмотреть
    private val memberDao: MemberDao,
    private val purchaseDao: PurchaseDao,
    ) {
    val eventList: LiveData<List<Event>> = eventDao.getEventList()

    fun getEventList() { //toask нужно ли или удалить
        eventDao.getEventList()
    }

    suspend fun getEventById(eventId: Long): LiveData<Event> {
        return eventDao.getEventById(eventId)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun addEvent(event: Event) {
        eventDao.addEvent(event)
    }

    suspend fun getMemberListByEventId(eventId: Long): LiveData<List<Member>> {
        return memberDao.getMembersByEventId(eventId)
    }

    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }

    suspend fun deleteEventByIds(eventIds: List<Long>) {
        eventDao.deleteEventsByIds(eventIds)
    }
}