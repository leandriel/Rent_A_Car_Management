package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.api.service.CarService
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferences
import com.leandroid.system.rentacarmanagement.model.Brand
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.Color
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarDataSourceImpl(private val sharedPreferences: SharedPreferences, service: CarService): CarDataSource {
    private val cars = mutableListOf<Car>(Car("1","asd321","ka",Brand("brandID","brandName"),true, Color("1", "rojo"),"asdasd"))
    private val brands = mutableListOf<Brand>(Brand("1", "FORD"), Brand("1", "FIAT"), Brand("1", "HONDA"))
    private val colors = mutableListOf<Color>(Color("1", "Azul"), Color("3", "Blanco"), Color("2", "Negro"))

    override fun getCars(): ApiResponse<MutableList<Car>> {
        return ApiResponse(200, true, "Autos", cars)
    }

    override fun getCar(id: String): ApiResponse<CarDTO> {
        return ApiResponse(200, true, "Auto", CarDTO(cars.find { it.id == id } ?: Car(), brands, colors))
    }

    override fun saveCar(car: Car): ApiResponse<MutableList<Car>> {
        cars.add(car)
        return ApiResponse(200, true, "Autos", cars)
    }

    override fun updateCar(car: Car): ApiResponse<MutableList<Car>> {
        cars.indexOfFirst {
            it.id == car.id
        }.also {
            cars[it] = car
        }

        return ApiResponse(200, true, "Autos", cars)
    }

    override fun deleteCar(id: String): ApiResponse<MutableList<Car>> {
        cars.indexOfFirst {
            it.id === id
        }.also {
            cars.removeAt(it)
        }
        return ApiResponse(200, true, "Autos", cars)
    }

    override fun getUser(): Flow<String> = flow { sharedPreferences.getUser() }
}