package com.leandroid.system.rentacarmanagement.data.repository

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSource
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CarRepositoryImpl(private val dataSource: CarDataSource) : CarRepository {

    override fun getCars(): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.getCars()
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun getCar(id: String): Flow<Response<ApiResponse<CarDTO>>> = flow {
        try {
            val apiResponse = dataSource.getCar(id)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun saveCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.saveCar(car)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun updateCar(car: Car): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.updateCar(car)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun deleteCar(id: String): Flow<Response<ApiResponse<MutableList<Car>>>> = flow {
        try {
            val apiResponse = dataSource.deleteCar(id)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun getUser(): Flow<Response<User>> = flow {
        try {
            dataSource.getUser().map {
                try {
                    val user = Gson().fromJson(it, User::class.java)
                    emit(Response.Success(user))
                } catch (e: JsonSyntaxException) {
                    emit(Response.Error(e))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }
}