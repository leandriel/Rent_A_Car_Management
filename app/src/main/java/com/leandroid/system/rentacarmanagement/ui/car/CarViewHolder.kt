package com.leandroid.system.rentacarmanagement.ui.car

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemCarBinding
import com.leandroid.system.rentacarmanagement.model.Car

class CarViewHolder(view: View, private val listener: CarListener) : RecyclerView.ViewHolder(view),
    PopupMenu.OnMenuItemClickListener {
    private val binding = ItemCarBinding.bind(view)

    fun bind(car: Car) {
        binding.root.setOnClickListener {
            listener.onClick(car.id)
        }

        binding.iBtnMenu.setOnClickListener {
            showPopupMenu(it)
        }

        binding.tvDetails.text = car.carDetails
        binding.tvComment.text = car.comment
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
            R.id.action_popup_edit -> {
                listener.onMenuClickEdit(absoluteAdapterPosition)
                true
            }
            R.id.action_popup_delete -> {
                listener.onMenuClickDelete(absoluteAdapterPosition)
                true
            }
            else -> false
        }
    }
}