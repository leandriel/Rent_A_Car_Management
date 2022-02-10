package com.leandroid.system.rentacarmanagement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.EventWrapper

class HomeCarViewModel : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private val _isCreate = MutableLiveData<EventWrapper<Boolean>>()
    val isCreate: LiveData<EventWrapper<Boolean>> = _isCreate

    fun setSearchText(text: String){
        _searchText.value = text
    }

    fun create(){
        _isCreate.value = EventWrapper(true)
    }
}