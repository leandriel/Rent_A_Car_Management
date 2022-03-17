package com.leandroid.system.rentacarmanagement.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.repository.LoginRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.EventWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _user: MutableLiveData<DataState<User>> =
        MutableLiveData(DataState.Idle)
    val user: LiveData<DataState<User>> = _user

    private val _userSaved = MutableLiveData<EventWrapper<Boolean>>()
    val userSaved: LiveData<EventWrapper<Boolean>> = _userSaved

    fun doLogin(email: String, pass: String){
        viewModelScope.launch {
            repository.doLogin(email, pass).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _user.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _user.value = DataState.Success(it.data.data ?: User())
                    }
                    is Response.Error -> {
                        _user.value = DataState.Loading(loading = false)
                        _user.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.saveUser(user).onEach {
                _userSaved.value = EventWrapper(it)
            }.launchIn(this)
        }
    }
}