package com.leandroid.system.rentacarmanagement.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BookingViewModel(private val repository: BookingRepository) : ViewModel() {
    private val _bookingsDetails: MutableLiveData<DataState<MutableList<BookingDetails>>> =
        MutableLiveData(DataState.Idle)
    val bookingsDetails: LiveData<DataState<MutableList<BookingDetails>>> = _bookingsDetails

    private val _bookingDetails: MutableLiveData<DataState<BookingDetails>> =
        MutableLiveData(DataState.Idle)
    val bookingDetails: LiveData<DataState<BookingDetails>> = _bookingDetails

    fun getAllCars() {
        viewModelScope.launch {
            repository.getAllCars().onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingsDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCarsByDate(date: String) {
        viewModelScope.launch {
            repository.getCarsByDate(date).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingsDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCarDetails(id: String) {
        viewModelScope.launch {
            repository.getCarDetails(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingDetails.value = DataState.Success(it.data.data ?: BookingDetails())
                    }
                    is Response.Error -> {
                        _bookingDetails.value = DataState.Loading(loading = false)
                        _bookingDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun saveBooking(booking: Booking) {
        viewModelScope.launch {
            repository.saveBooking(booking).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingsDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateBooking(booking: Booking) {
        viewModelScope.launch {
            repository.updateBooking(booking).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingsDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun deleteBooking(id: String) {
        viewModelScope.launch {
            repository.deleteBooking(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingsDetails.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }
}