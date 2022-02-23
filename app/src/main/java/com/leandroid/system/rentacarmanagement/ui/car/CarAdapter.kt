package com.leandroid.system.rentacarmanagement.ui.car

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class CarAdapter(private val listener: RecyclerListener) : RecyclerView.Adapter<CarViewHolder>() {

    private var originCars: MutableList<Car> = mutableListOf()
    private var cars: MutableList<Car> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarViewHolder(layoutInflater.inflate(R.layout.item_car, parent, false), listener)
    }

    override fun onBindViewHolder(holderCar: CarViewHolder, position: Int) {
        val item = cars[position]
        holderCar.bind(item)
    }

    fun getItemByPosition(position: Int) = cars[position]

    fun filterByBrand(text: String) {
        if(text.isEmpty())
            cars = originCars
        else
            cars = originCars.filter { it.brand.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cars.size

    fun setCars(cars: MutableList<Car>) {
        this.originCars = cars
        this.cars = cars
        notifyDataSetChanged()
    }
}