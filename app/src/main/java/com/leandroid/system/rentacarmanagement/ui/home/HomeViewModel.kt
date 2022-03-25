package com.leandroid.system.rentacarmanagement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.repository.HomeRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _user: MutableLiveData<DataState<User?>> =
        MutableLiveData(DataState.Idle)
    val user: LiveData<DataState<User?>> = _user

    fun getUser() {
        viewModelScope.launch {
            _user.value = DataState.Loading(loading = true)
            repository.getUser().map {
                _user.value = DataState.Success(it)
            }.catch { e ->
                _user.value = DataState.Error(e)
            }.launchIn(this)
        }
    }
}