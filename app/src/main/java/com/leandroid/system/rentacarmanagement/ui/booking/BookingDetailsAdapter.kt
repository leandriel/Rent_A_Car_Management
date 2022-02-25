package com.leandroid.system.rentacarmanagement.ui.booking

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemParentBookingBinding
import com.leandroid.system.rentacarmanagement.databinding.ItemSubBookingBinding
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.utils.ExpandableRecyclerViewAdapter
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingDetailsAdapter(
    bookings: MutableList<BookingDetails>,
    private val listener: RecyclerListener
) :
    ExpandableRecyclerViewAdapter<Booking,
            BookingDetails,
            BookingDetailsAdapter.PViewHolder,
            BookingDetailsAdapter.CViewHolder<Booking>>(
        bookings,
        ExpandingDirection.VERTICAL,
        true,
        false
    ), PopupMenu.OnMenuItemClickListener  {

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

    class CViewHolder<T>(v: View, private val listener: RecyclerListener, private val binding: ItemSubBookingBinding) :
        ExpandableRecyclerViewAdapter.ExpandedViewHolder(v) {
        fun bind(booking: T) {
            binding.run {
                (booking as Booking).let {
                    tvDateStart.text = it.startDateString
                    tvDateEnd.text = it.endDateString
                    tvDrivingLicence.text = it.drivingLicenseString
                    tvFly.text = it.flyString
                    tvHotel.text = it.hotelString
                    tvReturnCar.text = it.returnCar.placeDatetimeString
                    tvPrice.text = it.priceString
                    tvCommission.text = it.commissionString
//                    root.setOnClickListener {
//                        showPopupMenu(it)
//                    }
                }
            }
        }

//        private fun showPopupMenu(view: View) {
//            PopupMenu(view.context, view).apply {
//                inflate(R.menu.menu_popup)
//                setOnMenuItemClickListener(this@CViewHolder)
//                show()
//            }
//        }
//
//        override fun onMenuItemClick(item: MenuItem?): Boolean {
//            return when (item?.itemId) {
//                R.id.action_popup_edit -> {
//                    listener.onMenuClickEdit(absoluteAdapterPosition)
//                    true
//                }
//                R.id.action_popup_delete -> {
//                    listener.onMenuClickDelete(absoluteAdapterPosition)
//                    true
//                }
//                else -> false
//            }
//        }
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
        with(parentViewHolder.containerView) {
            findViewById<TextView>(R.id.tv_details).text = expandableType.car.carDetails
            findViewById<TextView>(R.id.tv_comment).text = expandableType.car.comment
            findViewById<TextView>(R.id.tv_state).text = expandableType.carState
            findViewById<TextView>(R.id.tv_state).setTextColor(
                ContextCompat.getColor(
                    context,
                    expandableType.carStateColor
                )
            )
            findViewById<TextView>(R.id.tv_next_booking).text = expandableType.nextBookingString
            findViewById<ImageView>(R.id.i_btn_arrow).setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (expandableType.isExpanded) R.drawable.ic_up_arrow else R.drawable.ic_down_float,
                    null
                )
            )
        }
    }

    override fun onCreateChildViewHolder(
        child: ViewGroup,
        viewType: Int,
        position: Int
    ): CViewHolder<Booking> {
        val layoutInflater = LayoutInflater.from(child.context)
        val binding = ItemSubBookingBinding.inflate(layoutInflater, child, false)
        return CViewHolder(
            binding.root, listener, binding
        )
    }

    override fun onBindChildViewHolder(
        childViewHolder: CViewHolder<Booking>,
        expandedType: Any,
        expandableType: BookingDetails,
        position: Int
    ) {
        childViewHolder.bind(expandedType as Booking)
    }

    override fun onExpandableClick(
        expandableViewHolder: PViewHolder,
        expandableType: BookingDetails
    ) {
        print(expandableType)
    }

    override fun onExpandedClick(
        expandableViewHolder: PViewHolder,
        expandedViewHolder: CViewHolder<Booking>,
        expandedType: Any,
        expandableType: BookingDetails
    ) {
        showPopupMenu(expandedViewHolder.containerView)
    }

    private fun showPopupMenu(view: View) {
        PopupMenu(view.context, view).apply {
            inflate(R.menu.menu_popup)
            setOnMenuItemClickListener(this@BookingDetailsAdapter)
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