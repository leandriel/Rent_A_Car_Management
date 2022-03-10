package com.leandroid.system.rentacarmanagement.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.data.repository.CarRepository
import com.leandroid.system.rentacarmanagement.data.repository.LoginRepository

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepository::class.java)
            .newInstance(repository)
    }
}