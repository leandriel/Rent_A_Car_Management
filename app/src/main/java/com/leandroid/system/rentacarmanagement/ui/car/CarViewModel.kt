package com.leandroid.system.rentacarmanagement.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.repository.CarRepository
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository) : ViewModel() {

    private val _cars: MutableLiveData<DataState<MutableList<Car>>> =
        MutableLiveData(DataState.Idle)
    val cars: LiveData<DataState<MutableList<Car>>> = _cars

    private val _carDTO: MutableLiveData<DataState<CarDTO>> =
        MutableLiveData(DataState.Idle)
    val carDTO: LiveData<DataState<CarDTO>> = _carDTO

    fun getCars() {
        viewModelScope.launch {
            repository.getCars().onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _cars.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _cars.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _cars.value = DataState.Loading(loading = false)
                        _cars.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getCar(id: String) {
        viewModelScope.launch {
            repository.getCar(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _carDTO.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _carDTO.value = DataState.Success(it.data.data ?: CarDTO())
                    }
                    is Response.Error -> {
                        _carDTO.value = DataState.Loading(loading = false)
                        _carDTO.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun saveCar(car: Car) {
        viewModelScope.launch {
            repository.saveCar(car).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _cars.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _cars.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _cars.value = DataState.Loading(loading = false)
                        _cars.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch {
            repository.updateCar(car).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _cars.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _cars.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _cars.value = DataState.Loading(loading = false)
                        _cars.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }

    fun deleteCar(id: String) {
        viewModelScope.launch {
            repository.deleteCar(id).onEach {
                when (it) {
                    is Response.NotInitialized, Response.Loading -> {
                        _cars.value = DataState.Loading(loading = true)
                    }
                    is Response.Success -> {
                        _cars.value = DataState.Success(it.data.data ?: mutableListOf())
                    }
                    is Response.Error -> {
                        _cars.value = DataState.Loading(loading = false)
                        _cars.value = DataState.Error(it.exception)
                    }
                }
            }.launchIn(this)
        }
    }
}