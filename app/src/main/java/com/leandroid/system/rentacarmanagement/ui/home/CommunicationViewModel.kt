package com.leandroid.system.rentacarmanagement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.EventWrapper

class CommunicationViewModel : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private val _isCreateCar = MutableLiveData<EventWrapper<Boolean>>()
    val isCreateCar: LiveData<EventWrapper<Boolean>> = _isCreateCar

    private val _isCreateBooking = MutableLiveData<EventWrapper<Boolean>>()
    val isCreateBooking: LiveData<EventWrapper<Boolean>> = _isCreateBooking

    fun setSearchText(text: String){
        _searchText.value = text
    }

    fun createCar(){
        _isCreateCar.value = EventWrapper(true)
    }

    fun createBooking(){
        _isCreateBooking.value = EventWrapper(true)
    }
}