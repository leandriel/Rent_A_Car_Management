package com.leandroid.system.rentacarmanagement.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.EventWrapper
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

    private val _bookingDTO: MutableLiveData<DataState<BookingDTO>> =
        MutableLiveData(DataState.Idle)
    val bookingDTO: LiveData<DataState<BookingDTO>> = _bookingDTO

    private val _saveSuccess = MutableLiveData<EventWrapper<Boolean>>()
    val saveSuccess: LiveData<EventWrapper<Boolean>> = _saveSuccess
    private val _updateSuccess = MutableLiveData<EventWrapper<Boolean>>()
    val updateSuccess: LiveData<EventWrapper<Boolean>> = _updateSuccess

    fun getBookingsByDate(date: String){
        viewModelScope.launch {
            repository.getBookingsByDate(date).onEach {
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
//    fun getAllCars() {
//        viewModelScope.launch {
//            repository.getAllCars().onEach {
//                when (it) {
//                    is Response.NotInitialized, Response.Loading -> {
//                        _bookingsDetails.value = DataState.Loading(loading = true)
//                    }
//                    is Response.Success -> {
//                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
//                    }
//                    is Response.Error -> {
//                        _bookingsDetails.value = DataState.Loading(loading = false)
//                        _bookingsDetails.value = DataState.Error(it.exception)
//                    }
//                }
//            }.launchIn(this)
//        }
//    }
//
//    fun getCarsByDate(date: String) {
//        viewModelScope.launch {
//            repository.getCarsByDate(date).onEach {
//                when (it) {
//                    is Response.NotInitialized, Response.Loading -> {
//                        _bookingsDetails.value = DataState.Loading(loading = true)
//                    }
//                    is Response.Success -> {
//                        _bookingsDetails.value = DataState.Success(it.data.data ?: mutableListOf())
//                    }
//                    is Response.Error -> {
//                        _bookingsDetails.value = DataState.Loading(loading = false)
//                        _bookingsDetails.value = DataState.Error(it.exception)
//                    }
//                }
//            }.launchIn(this)
//        }
//    }
//
    fun getBooking(id: String) {
        viewModelScope.launch {
            repository.getBooking(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _bookingDTO.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _bookingDTO.value = DataState.Success(it.data.data ?: BookingDTO())
                    }
                    is Response.Error -> {
                        _bookingDTO.value = DataState.Loading(loading = false)
                        _bookingDTO.value = DataState.Error(it.exception)
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
                        _saveSuccess.value = EventWrapper(true)
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                        _saveSuccess.value = EventWrapper(false)
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
                        _updateSuccess.value = EventWrapper(true)
                    }
                    is Response.Error -> {
                        _bookingsDetails.value = DataState.Loading(loading = false)
                        _bookingsDetails.value = DataState.Error(it.exception)
                        _updateSuccess.value = EventWrapper(false)
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