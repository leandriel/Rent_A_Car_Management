package com.leandroid.system.rentacarmanagement.data.datasource

import com.leandroid.system.rentacarmanagement.data.utils.ApiResponse
import com.leandroid.system.rentacarmanagement.model.*
import java.util.*

class BookingDataSourceImpl : BookingDataSource {
    private val bookingsDetails = mutableListOf<BookingDetails>(
        BookingDetails(
            "1",
            Car("1","asd321","ka",
                Brand("brandID","brandName"),true, Color("1", "rojo"),"asdasd"),
            mutableListOf<Booking>(
                Booking(
                    "1",
                    Car("1","asd321","ka",
                        Brand("brandID","brandName"),true, Color("1", "rojo"),"asdasd"),
                    "Fll",
                    "Hotel 1",
                    "22212",
                    "12/02/2022",
                    "20/02/2022",
                    ReturnCar(
                        "1",
                        "Place 1",
                        "20/02/2022 12:00"
                    ),
                    "1222",
                    "122"
                ),
                Booking(
                    "2",
                    Car("1","asd321","ka",
                        Brand("brandID","brandName"),true, Color("1", "rojo"),"asdasd"),
                    "ksksk",
                    "Hotel 2",
                    "2kkkk",
                    "20/02/2022",
                    "27/02/2022",
                    ReturnCar(
                        "2",
                        "Place 2",
                        "27/02/2022 13:00"
                    ),
                    "1400",
                    "120"
                )
            )
        ),BookingDetails(
            "2",
            Car("2","sksksks","kall",
                Brand("brandID2","brandName2"),true, Color("2", "blaco"),"asdasd"),
            mutableListOf<Booking>(
                Booking(
                    "3",
                    Car("2","sksksks","kall",
                        Brand("brandID2","brandName2"),true, Color("2", "blaco"),"asdasd"),
                    "mdkdkd",
                    "Hotel 3",
                    "0003",
                    "12/02/2022",
                    "20/02/2022",
                    ReturnCar(
                        "3",
                        "Place 3",
                        "20/02/2022 12:00"
                    ),
                    "2000",
                    "112"
                ),
                Booking(
                    "4",
                    Car("2","sksksks","kall",
                        Brand("brandID2","brandName2"),true, Color("2", "blaco"),"asdasd"),
                    "msmsms",
                    "Hotel 4",
                    "p0022",
                    "20/02/2022",
                    "27/02/2022",
                    ReturnCar(
                        "4",
                        "Place 4",
                        "27/02/2022 13:00"
                    ),
                    "1405",
                    "150"
                )
            )
        )
    )
    private val cars = mutableListOf<Car>(
        Car("1","asd321","ka",
            Brand("brandID","brandName"),true, Color("1", "rojo"),"asdasd")
    )
    private val brands = mutableListOf<Brand>(Brand("1", "FORD"), Brand("1", "FIAT"), Brand("1", "HONDA"))
    private val colors = mutableListOf<Color>(Color("1", "Azul"), Color("3", "Blanco"), Color("2", "Negro"))


    override fun getBookingsByDate(date: String): ApiResponse<MutableList<BookingDetails>> {
        return ApiResponse(200, true, "Reservas", bookingsDetails)
    }

//    override fun getAllCars(): ApiResponse<MutableList<BookingDetails>> {
//        return ApiResponse(200, true, "Reservas", bookingsDetails)
//    }

//    override fun getCarsByDate(date: String): ApiResponse<MutableList<BookingDetails>> {
////        bookingsDetails.filter {
////            it.bookings.map {
////                it.endDate
////            }
////        }
//        return ApiResponse(200, true, "Reservas", bookingsDetails)
//    }

//    override fun getCarDetails(id: String): ApiResponse<BookingDetails> {
//        bookingsDetails.indexOfFirst {
//            it.car.id === id
//        }.also {
//            return ApiResponse(200, true, "Reservas", bookingsDetails[it])
//        }
//    }

    override fun saveBooking(booking: Booking): ApiResponse<MutableList<BookingDetails>> {
        bookingsDetails.indexOfFirst {
            it.car.id == booking.car.id
        }.also {
            if (it == -1) {
                bookingsDetails.add(BookingDetails(UUID.randomUUID().toString(), booking.car, mutableListOf(booking)))
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