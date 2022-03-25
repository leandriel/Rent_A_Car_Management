package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getCars(): Flow<Response<ApiResponse<MutableList<Car>>>>
    fun getCar(id: String): Flow<Response<ApiResponse<CarDTO>>>
    fun saveCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>>
    fun updateCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>>
    fun deleteCar(id: String): Flow<Response<ApiResponse<MutableList<Car>>>>
    fun getUser(): Flow<Response<User>>
}