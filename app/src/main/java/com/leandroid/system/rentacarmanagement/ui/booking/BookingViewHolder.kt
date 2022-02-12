package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.databinding.ItemBookingBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.car.CarListener

class BookingViewHolder (view: View, private val listener: CarListener) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBookingBinding.bind(view)

    fun bind(car: Car) {
        binding.root.setOnClickListener {
            listener.onClick(car.id)
        }

        binding.tvDetailsBooking.text = car.carDetails
        binding.tvComment.text = car.comment
//        Picasso.get().load(movie.urlImage).into(binding.movieImage)
    }
}