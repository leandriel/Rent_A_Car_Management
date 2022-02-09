package com.leandroid.system.rentacarmanagement.ui.car

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.model.Car

class CarAdapter(private val listener: CarListener): RecyclerView.Adapter<CarViewHolder>() {
    private var cars:MutableList<Car> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
    }

    override fun onBindViewHolder(holderCar: CarViewHolder, position: Int) {
        val item = cars[position]
        holderCar.bind(item)
    }

    override fun getItemCount(): Int = cars.size

    fun setCars(cars: MutableList<Car>){
        this.cars = cars
        notifyDataSetChanged()
    }
}