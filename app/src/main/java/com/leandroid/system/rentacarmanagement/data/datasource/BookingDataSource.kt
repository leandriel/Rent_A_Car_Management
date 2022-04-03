package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails

interface BookingDataSource {
   fun getBookingsByDate(date:String, onlyAvailable: Boolean): ApiResponse<MutableList<BookingDetails>>
   fun getBooking(id: String): ApiResponse<BookingDTO>
   fun saveBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>
   fun updateBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>
   fun deleteBooking(id: String): ApiResponse<MutableList<BookingDetails>>
}