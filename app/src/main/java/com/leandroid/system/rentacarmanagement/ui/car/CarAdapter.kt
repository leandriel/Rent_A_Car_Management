package com.leandroid.system.rentacarmanagement.ui.car

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.model.Car

class CarAdapter(private val listener: CarListener): RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var cars:MutableList<Car> = mutableListOf()gi


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAdapter.CarViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CarAdapter.CarViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}