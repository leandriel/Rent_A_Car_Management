package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.car.CarListener
import com.leandroid.system.rentacarmanagement.ui.car.CarViewHolder

class BookingAdapter(private val listener: CarListener): RecyclerView.Adapter<CarViewHolder>() {
    private var cars: MutableList<Car> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarViewHolder(layoutInflater.inflate(R.layout.item_booking, parent, false), listener)
    }

    override fun onBindViewHolder(holderCar: CarViewHolder, position: Int) {
        val item = cars[position]
        holderCar.bind(item)
    }

    override fun getItemCount(): Int = cars.size

    fun setCars(cars: MutableList<Car>) {
        this.cars = cars
        notifyDataSetChanged()
    }
}