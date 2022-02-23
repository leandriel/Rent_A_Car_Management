package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemBookingBinding
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.ExpandableRecyclerViewAdapter
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingDetailsViewHolder(view: View, private val listener: RecyclerListener) :
    ExpandableRecyclerViewAdapter.ExpandableViewHolder(view)