package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemBookingBinding
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingViewHolder(view: View, private val listener: RecyclerListener) : RecyclerView.ViewHolder(view),
    PopupMenu.OnMenuItemClickListener {
    private val binding = ItemBookingBinding.bind(view)

    fun bind(booking: BookingDetails) {
        binding.root.setOnClickListener {
            listener.onClick(booking.id)
        }

        binding.iBtnMenu.setOnClickListener {
            showPopupMenu(it)
        }

        binding.tvDetails.text = booking.car.carDetails
        binding.tvNextBooking.text = booking.nextBookingString
//        Picasso.get().load(movie.urlImage).into(binding.movieImage)
    }


    private fun showPopupMenu(view: View) {
        PopupMenu(binding.root.context, view).apply {
            inflate(R.menu.menu_popup)
            setOnMenuItemClickListener(this@BookingViewHolder)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_popup_edit -> {
                //listener.onMenuClickEdit(absoluteAdapterPosition)
                true
            }
            R.id.action_popup_delete -> {
                //listener.onMenuClickDelete(absoluteAdapterPosition)
                true
            }
            else -> false
        }
    }
}