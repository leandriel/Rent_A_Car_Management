package com.leandroid.system.rentacarmanagement.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepository
import com.leandroid.system.rentacarmanagement.data.repository.CarRepository

class BookingViewModelFactory(private val repository: BookingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BookingRepository::class.java)
            .newInstance(repository)
    }
}