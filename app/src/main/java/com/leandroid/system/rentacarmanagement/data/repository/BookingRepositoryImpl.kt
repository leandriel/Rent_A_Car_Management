package com.leandroid.system.rentacarmanagement.data.repository

import com.leandroid.system.rentacarmanagement.data.datasource.BookingDataSource
import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.data.utils.Response
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookingRepositoryImpl(private val dataSource: BookingDataSource) : BookingRepository {

    override fun getBookingsByDate(date: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> = flow {
        try {
            val apiResponse = dataSource.getBookingsByDate(date)
            emit(Response.Success(apiResponse))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

//    override fun getAllCars(): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> = flow {
//        try {
//            val apiResponse = dataSource.getBookingsByDate("")
//            emit(Response.Success(apiResponse))
//        } catch (e: Exception) {
//            emit(Response.Error(e))
//        }
//    }
//
//    override fun getCarsByDate(date: String): Flow<Response<ApiResponse<MutableList<BookingDetails>>>> =
//        flow {
//            try {
//                val apiResponse = dataSource.getBookingsByDate(date)
//                emit(Response.Success(apiResponse))
//            } catch (e: Exception) {
//                emit(Response.Error(e))
//            }
//        }
//
//    override fun getCarDetails(id: String): Flow<Response<ApiResponse<BookingDetails>>> = flow {
//        try {
//            val apiResponse = dataSource.getCarDetails(id)
//            emit(Response.Success(apiResponse))
//        } catch (e: Exception) {
//            emit(Response.Error(e))
//        }
//    }


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