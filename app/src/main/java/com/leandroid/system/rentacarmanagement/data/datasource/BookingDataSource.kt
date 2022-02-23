package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails

interface BookingDataSource {
//   fun getAllCars(): ApiResponse<MutableList<BookingDetails>>
//   fun getCarsByDate(date: String): ApiResponse<MutableList<BookingDetails>>
//   fun getCarDetails(id: String): ApiResponse<BookingDetails>

   fun getBookingsByDate(date:String): ApiResponse<MutableList<BookingDetails>>
   fun saveBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>
   fun updateBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>
   fun deleteBooking(id: String): ApiResponse<MutableList<BookingDetails>>
}