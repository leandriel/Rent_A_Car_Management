package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    fun getBooking(id: String): Flow<Response<ApiResponse<BookingDTO>>>
    fun getBookingsByDate(date:String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun saveBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun updateBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
    fun deleteBooking(id: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>>
}