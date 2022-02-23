package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingAdapter(private val listener: RecyclerListener) : RecyclerView.Adapter<BookingViewHolder>() {

    private var originBookings: MutableList<BookingDetails> = mutableListOf()
    private var bookings: MutableList<BookingDetails> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookingViewHolder(layoutInflater.inflate(R.layout.item_booking, parent, false), listener)
    }

    override fun onBindViewHolder(holderCar: BookingViewHolder, position: Int) {
        val item = bookings[position]
        holderCar.bind(item)
    }

    fun getItemByPosition(position: Int) = bookings[position]

    fun filterByBrand(text: String) {
        if(text.isEmpty())
            bookings = originBookings
        else
            bookings = originBookings.filter { it.car.brand.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = bookings.size

    fun setBookings(bookings: MutableList<BookingDetails>) {
        this.originBookings = bookings
        this.bookings = bookings
        notifyDataSetChanged()
    }
}