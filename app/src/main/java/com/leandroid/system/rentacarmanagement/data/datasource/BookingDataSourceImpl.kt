package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails

class BookingDataSourceImpl : BookingDataSource {
    private val bookingsDetails = mutableListOf<BookingDetails>()

    override fun getAllCars(): ApiResponse<MutableList<BookingDetails>> {
        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }

    override fun getCarsByDate(date: String): ApiResponse<MutableList<BookingDetails>> {
//        bookingsDetails.filter {
//            it.bookings.map {
//                it.endDate
//            }
//        }
        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }

    override fun getCarDetails(id: String): ApiResponse<BookingDetails> {
        bookingsDetails.indexOfFirst {
            it.car.id === id
        }.also {
            return ApiResponse(200, true, "Reservas", bookingsDetails[it])
        }
    }

    override fun saveBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>> {
        bookingsDetails.indexOfFirst {
            it.car.id === booking.car.id
        }.also {
            if (it == -1) {
                bookingsDetails.add(BookingDetails(booking.car, mutableListOf(booking)))
            } else {
                bookingsDetails[it].apply {
                    bookings.add(booking)
                }
            }
        }

        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }

    override fun updateBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>> {
        bookingsDetails.indexOfFirst {
            it.car.id == booking.car.id
        }.also {
            bookingsDetails[it].apply {
                bookings.indexOfFirst {
                    it.id == booking.id
                }.also {
                    bookings[it] = booking
                }
            }
        }

        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }

    override fun deleteBooking(id: String): ApiResponse<MutableList<BookingDetails>> {
        bookingsDetails.map { details ->
            details.bookings.indexOfFirst { booking ->
                booking.id == id
            }.also {
                details.bookings.removeAt(it)
            }
        }
        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }
}