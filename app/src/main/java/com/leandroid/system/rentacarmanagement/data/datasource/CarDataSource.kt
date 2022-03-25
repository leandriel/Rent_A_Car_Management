package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Car
import kotlinx.coroutines.flow.Flow


interface CarDataSource {
   fun getCars(): ApiResponse<MutableList<Car>>
   fun getCar(id: String): ApiResponse<CarDTO>
   fun saveCar(car: Car): ApiResponse<MutableList<Car>>
   fun updateCar(car: Car): ApiResponse<MutableList<Car>>
   fun deleteCar(id: String): ApiResponse<MutableList<Car>>
   fun getUser(): Flow<String>
}