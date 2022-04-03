package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.BookingDataSource
import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.api.response.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookingRepositoryImpl(private val dataSource: BookingDataSource) : BookingRepository {

    override fun getBooking(id: String): Flow<Response<ApiResponse<BookingDTO>>> = flow {
        try {
            val apiResponse = dataSource.getBooking(id)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun getBookingsByDate(date: String, onlyAvailable: Boolean): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> = flow {
        try {
            val apiResponse = dataSource.getBookingsByDate(date, onlyAvailable)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    override fun saveBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> =
        flow {
            try {
                val apiResponse = dataSource.saveBooking(booking)
                emit(Response.Success(apiResponse))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }

    override fun updateBooking(booking: Booking): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> =
        flow {
            try {
                val apiResponse = dataSource.updateBooking(booking)
                emit(Response.Success(apiResponse))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }

    override fun deleteBooking(id: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> =
        flow {
            try {
                val apiResponse = dataSource.deleteBooking(id)
                emit(Response.Success(apiResponse))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
}