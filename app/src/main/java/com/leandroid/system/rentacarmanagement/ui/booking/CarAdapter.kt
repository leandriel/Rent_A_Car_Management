package com.leandroid.system.rentacarmanagement.ui.booking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.leandroid.system.rentacarmanagement.databinding.ItemSpinnerBinding
import com.leandroid.system.rentacarmanagement.model.Car

class CarAdapter(
    context: Context,
    resource: Int,
) : ArrayAdapter<Car>(context, resource, mutableListOf()) {

    private val cars = mutableListOf<Car>()

    override fun getItem(position: Int): Car {
        return cars[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return cars.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemSpinnerBinding.inflate(inflater, parent, false)
        val car = getCarForPosition(position)
        bind(binding, car)
        return binding.root
    }

    fun getCarForPosition(position: Int): Car {
        return cars[position]
    }

    fun getPositionByCar(car: Car): Int {
        return cars.indexOf(car)
    }

    private fun bind(binding: ItemSpinnerBinding, car: Car) {
        binding.tvName.text = car.carDetails
    }

    fun setCars(cars: MutableList<Car>) {
        this.cars.clear()
        this.cars.addAll(cars)

        notifyDataSetChanged()
    }
}
