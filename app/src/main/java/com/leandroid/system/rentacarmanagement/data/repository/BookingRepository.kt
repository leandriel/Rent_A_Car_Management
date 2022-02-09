package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    fun getAllCars(): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun getCarsByDate(date: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun getCarDetails(id: String): Flow<Response<ApiResponse<BookingDetails>>>

    fun saveBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun updateBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun deleteBooking(id: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
}