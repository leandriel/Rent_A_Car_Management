package com.leandroid.system.rentacarmanagement.data.api.service

import com.leandroid.system.rentacarmanagement.data.api.Retrofit
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import retrofit2.http.*

interface BookingService {
    @GET("bookings/users/{userId}/companies/{companyId}")
    fun getBookingsByDate(
        @Header("Authorization") token: String,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?,
        @Query("date") date: String?,
        @Query("onlyAvailable") onlyAvailable: Boolean?
    ): ApiResponse<MutableList<BookingDetails>>

    @GET("bookings/{bookingId}/users/{userId}/companies/{companyId}")
    fun getBooking(
        @Header("Authorization") token: String,
        @Path("bookingId") bookingId: String?,
        @Path("userId") userId: String?,
        @Path("companyId") companyId: String?
    ): ApiResponse<BookingDTO>

    @POST("bookings")
    fun saveBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>

    @PUT("bookings")
    fun updateBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>>

    @PUT("bookings")
    fun deleteBooking(id: String): ApiResponse<MutableList<BookingDetails>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            baseUrl: String
        ): BookingService {
            return Retrofit
                .invoke(connectivityInterceptor, baseUrl)
                .create(BookingService::class.java)
        }
    }
}