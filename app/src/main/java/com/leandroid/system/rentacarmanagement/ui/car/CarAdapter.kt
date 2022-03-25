package com.leandroid.system.rentacarmanagement.ui.car

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemCarBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class CarAdapter(private val listener: RecyclerListener) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var originCars: MutableList<Car> = mutableListOf()
    private var cars: MutableList<Car> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAdapter.CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarViewHolder(layoutInflater.inflate(R.layout.item_car, parent, false), listener)
    }

    override fun onBindViewHolder(holderCar: CarViewHolder, position: Int) {
        val item = cars[position]
        holderCar.bind(item)
    }

    fun getItemByPosition(position: Int) = cars[position]

    fun filterByBrand(text: String) {
        cars = if (text.isEmpty())
            originCars
        else
            originCars.filter { it.brand.name.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cars.size

    fun setCars(cars: MutableList<Car>) {
        this.originCars = cars
        this.cars = cars
        notifyDataSetChanged()
    }

    inner class CarViewHolder(view: View, private val listener: RecyclerListener) :
        RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener {
        private val binding = ItemCarBinding.bind(view)

        fun bind(car: Car) {
            with(binding) {
                root.setOnClickListener {
                    showPopupMenu(it)
                }

                tvDetails.text = car.carDetails
                tvComment.text = car.comment
            }
//        Picasso.get().load(movie.urlImage).into(binding.movieImage)
        }

        private fun showPopupMenu(view: View) {
            PopupMenu(binding.root.context, view).apply {
                inflate(R.menu.menu_popup)
                setOnMenuItemClickListener(this@CarViewHolder)
                show()
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.action_popup_show -> {
                    listener.onClick(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                R.id.action_popup_edit -> {
                    listener.onMenuClickEdit(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                R.id.action_popup_delete -> {
                    listener.onMenuClickDelete(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                else -> false
            }
        }
    }
}