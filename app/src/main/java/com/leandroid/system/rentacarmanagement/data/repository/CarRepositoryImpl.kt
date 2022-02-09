package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSource
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarRepositoryImpl(private val dataSource: CarDataSource): CarRepository {
    override fun getCars(): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
       try {
           val apiResponse = dataSource.getCars()
           emit(Response.Success(apiResponse))
       } catch (e:Exception){
           emit(Response.Error(e))
       }
    }

    override fun getCar(id: String): Flow<Response<ApiResponse<CarDTO>>> = flow {
        try {
            val apiResponse = dataSource.getCar(id)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun saveCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.saveCar(car)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun updateCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.updateCar(car)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }

    override fun deleteCar(id: String): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.deleteCar(id)
            emit(Response.Success(apiResponse))
        } catch (e:Exception){
            emit(Response.Error(e))
        }
    }
}