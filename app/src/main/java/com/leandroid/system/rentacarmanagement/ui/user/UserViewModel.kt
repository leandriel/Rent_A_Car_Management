package com.leandroid.system.rentacarmanagement.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.dto.UserDTO
import com.leandroid.system.rentacarmanagement.data.repository.UserRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.EventWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users: MutableLiveData<DataState<MutableList<User>>> =
        MutableLiveData(DataState.Idle)
    val users: LiveData<DataState<MutableList<User>>> = _users

    private val _userDTO: MutableLiveData<DataState<UserDTO>> =
        MutableLiveData(DataState.Idle)
    val userDTO: LiveData<DataState<UserDTO>> = _userDTO

    private val _user: MutableLiveData<DataState<User>> =
        MutableLiveData(DataState.Idle)
    val user: LiveData<DataState<User>> = _user

    private val _saveSuccess = MutableLiveData<EventWrapper<Boolean>>()
    val saveSuccess: LiveData<EventWrapper<Boolean>> = _saveSuccess
    private val _updateSuccess = MutableLiveData<EventWrapper<Boolean>>()
    val updateSuccess: LiveData<EventWrapper<Boolean>> = _updateSuccess

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers().onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _users.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _users.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _users.value = DataState.Loading(loading = false)
                        _users.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getUser(id: String) {
        viewModelScope.launch {
            repository.getUser(id).onEach {
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
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _users.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _users.value = DataState.Success(it.data.data ?: mutableListOf())
                        _saveSuccess.value = EventWrapper(true)
                    }
                    is Response.Error -> {
                        _users.value = DataState.Loading(loading = false)
                        _users.value = DataState.Error(it.exception)
                        _saveSuccess.value = EventWrapper(false)
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _users.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _users.value = DataState.Success(it.data.data ?: mutableListOf())
                        _updateSuccess.value = EventWrapper(true)
                    }
                    is Response.Error -> {
                        _users.value = DataState.Loading(loading = false)
                        _users.value = DataState.Error(it.exception)
                        _updateSuccess.value = EventWrapper(false)
                    }
                }
            }.launchIn(this)
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            repository.deleteUser(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _users.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _users.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _users.value = DataState.Loading(loading = false)
                        _users.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }
}