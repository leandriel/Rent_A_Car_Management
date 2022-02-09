package com.leandroid.system.rentacarmanagement.ui.car

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.databinding.ItemCarBinding
import com.leandroid.system.rentacarmanagement.model.Car

class CarViewHolder(view: View, private val listener: CarListener) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCarBinding.bind(view)

    fun bind(car: Car) {
        binding.root.setOnClickListener {
            listener.onClick(car.id)
        }

        binding.tvMark.text = car.originalTitle
        Picasso.get().load(movie.urlImage).into(binding.movieImage)
    }
}