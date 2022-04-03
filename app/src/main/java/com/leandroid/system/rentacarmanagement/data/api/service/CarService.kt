package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.api.Retrofit
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Car
import retrofit2.http.*

interface CarService {
    @GET("cars/users/{userId}/companies/{companyId}")
    fun getCars(
        @Header("Authorization") token: String,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?
    ): ApiResponse<MutableList<Car>>

    @GET("cars/{carId}/users/{userId}/companies/{companyId}")
    fun getCar(
        @Header("Authorization") token: String,
        @Path("carId") carId: String?,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?
    ): ApiResponse<CarDTO>

    @POST("cars")
    fun saveCar(
        @Header("Authorization") token: String,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        car: Car
    ): ApiResponse<MutableList<Car>>

    @PUT("cars")
    fun updateCar(
        @Header("Authorization") token: String,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        car: Car
    ): ApiResponse<MutableList<Car>>

    @PUT("cars")
    fun deleteCar(
        @Header("Authorization") token: String,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        id: String
    ): ApiResponse<MutableList<Car>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            baseUrl: String
        ): CarService {
            return Retrofit
                .invoke(connectivityInterceptor, baseUrl)
                .create(CarService::class.java)
        }
    }
}