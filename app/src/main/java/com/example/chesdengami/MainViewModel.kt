package com.example.chesdengami

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chesdengami.data.AppRepository
import com.example.chesdengami.model.Event
import com.example.chesdengami.model.Member
import com.example.chesdengami.model.Purchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    var eventList: LiveData<List<Event>> = appRepository.eventList
    var memberList: LiveData<List<Member>> = MutableLiveData()
    var purchaseList: LiveData<List<Purchase>> = MutableLiveData()
    var currentEvent: LiveData<Event> = MutableLiveData()

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) { appRepository.addEvent(event) } //toask посмотреть чем скоупы отличаются и вообще как с этим работать с корутинами
    }

    fun getEventById(eventId: Long) {
        viewModelScope.launch {
            currentEvent = appRepository.getEventById(eventId)
        }
    }

    fun updateEvent(eventName: String) {
        if (currentEvent.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                appRepository.updateEvent(
                    currentEvent.value!!.copy(
                        eventName = eventName
                    )
                )
            }
        }
    }

    fun getMemberListByCurrentEventId(eventId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            memberList = appRepository.getMemberListByEventId(eventId)
        }
    }

    fun addMember(member: Member) {
        viewModelScope.launch(Dispatchers.IO) { appRepository.addMember(member) }
    }

    fun deleteEventsByIds(eventIds: List<Long>) {
        viewModelScope.launch() { appRepository.deleteEventByIds(eventIds) }
    }

    class MainViewModelFactory(private val appRepository: AppRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(appRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}