package com.leandroid.system.rentacarmanagement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    fun setSearchText(text: String){
        print(text)
        _searchText.value = text
    }
}