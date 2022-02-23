package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemParentBookingBinding
import com.leandroid.system.rentacarmanagement.databinding.ItemSubBookingBinding
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.ExpandableRecyclerViewAdapter
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingDetailsAdapter(bookings: MutableList<BookingDetails>, private val listener: RecyclerListener) :
    ExpandableRecyclerViewAdapter<Booking,
            BookingDetails,
            BookingDetailsAdapter.PViewHolder,
            BookingDetailsAdapter.CViewHolder<Booking>>(
        bookings,
        ExpandingDirection.VERTICAL,
        true,
        false
    ) {

    //private var originBookings: MutableList<BookingDetails> = mutableListOf()
    //private var bookings: MutableList<BookingDetails> = mutableListOf()


//    override fun onBindViewHolder(holderCar: BookingViewHolder, position: Int) {
//        val item = bookings[position]
//        holderCar.bind(item)
//    }

//    fun getItemByPosition(position: Int) = bookings[position]
//
//    fun filterByBrand(text: String) {
//        if (text.isEmpty())
//            bookings = originBookings
//        else
//            bookings = originBookings.filter { it.car.brand.name.contains(text) }.toMutableList()
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount(): Int = bookings.size
//
//    fun setBookings(bookings: MutableList<BookingDetails>) {
//        this.originBookings = bookings
//        this.bookings = bookings
//        notifyDataSetChanged()
//    }

    class PViewHolder(v: View) : ExpandableRecyclerViewAdapter.ExpandableViewHolder(v)

    class CViewHolder<T>(v: View, private val binding: ItemSubBookingBinding) :
        ExpandableRecyclerViewAdapter.ExpandedViewHolder(v) {
        fun bind(booking: T, listener: RecyclerListener) {
            binding.run {
                (booking as Booking).let {
                    tvDateStart.text = it.startDate
                    tvDateEnd.text = it.endDate
                }
            }
        }
    }

    override fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemParentBookingBinding.inflate(layoutInflater, parent, false)
        return PViewHolder(
            binding.root
        )
    }

    override fun onBindParentViewHolder(
        parentViewHolder: PViewHolder,
        expandableType: BookingDetails,
        position: Int
    ) {
//        with(parentViewHolder.containerView) {
//            findViewById<ImageView>(R.id.i_btn_arrow).setImageDrawable(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    if (expandableType.isExpanded) R.drawable.ic_up_arrow else R.drawable.ic_down_float,
//                    null
//                )
//            )
//        }
    }

    override fun onCreateChildViewHolder(
        child: ViewGroup,
        viewType: Int,
        position: Int
    ): CViewHolder<Booking> {
        val layoutInflater = LayoutInflater.from(child.context)
        val binding = ItemSubBookingBinding.inflate(layoutInflater, child, false)
        return CViewHolder(
            binding.root, binding
        )
    }

    override fun onBindChildViewHolder(
        childViewHolder: CViewHolder<Booking>,
        expandedType: Any,
        expandableType: BookingDetails,
        position: Int
    ) {
        childViewHolder.bind(expandedType as Booking, listener)
    }

    override fun onExpandableClick(
        expandableViewHolder: PViewHolder,
        expandableType: BookingDetails
    ) {
    }

    override fun onExpandedClick(
        expandableViewHolder: PViewHolder,
        expandedViewHolder: CViewHolder<Booking>,
        expandedType: Any,
        expandableType: BookingDetails
    ) {
        }
}