package com.leandroid.system.rentacarmanagement.ui.car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.data.repository.CarRepository

class CarViewModelFactory(private val repository: CarRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CarRepository::class.java)
            .newInstance(repository)
    }
}