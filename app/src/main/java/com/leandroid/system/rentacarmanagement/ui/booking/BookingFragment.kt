package com.leandroid.system.rentacarmanagement.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.BookingDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepository
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentBookingBinding
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.ui.home.CommunicationViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.getDatePicker
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showDialog
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.DateTimeUtils.dateShortFormatString
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener
import java.util.*

class BookingFragment : Fragment(), RecyclerListener {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var bookingDetailsAdapter: BookingDetailsAdapter
    private lateinit var repository: BookingRepository
    private lateinit var viewModel: BookingViewModel
    private val communicationViewModel: CommunicationViewModel by activityViewModels()
    private var bookingsOrigin = mutableListOf<BookingDetails>()
    private var bookings = mutableListOf<BookingDetails>()
    private lateinit var datePicker: MaterialDatePicker<Long>
    private var selectedDate = Date().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = BookingRepositoryImpl(BookingDataSourceImpl(SharedPreferencesImpl(requireContext())))
        setUpViewModel()
        setUpObserverViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        initRecyclerView()
        setUpPickers()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            BookingViewModelFactory(repository)
        )[BookingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        getBookingsByDate( true)
        setDateText()
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            bookingsDetails.observe(requireActivity()) { state ->
                handleUiBookings(state)
            }
        }
        with(communicationViewModel) {
            searchText.observe(requireActivity()) { text ->
                bookings = if (text.isEmpty())
                    bookingsOrigin
                else
                    bookingsOrigin.filter { it.car.brand.name.contains(text) }.toMutableList()
                 bookingDetailsAdapter.addNewList(bookings as MutableList<Any>)
            }

            isCreateBooking.observe(requireActivity()) { isCreate ->
                isCreate.getContentIfNotHandled()?.let {
                    if (it) {
                        openBookingFragmentDialog()
                    }
                }

            }
        }
    }

    private fun setUpListener(){
        binding.cbBooking.setOnCheckedChangeListener { _, isChecked ->
            getBookingsByDate(isChecked)
        }
        binding.fabDate.setOnClickListener {
            datePicker.show(childFragmentManager, DATE_PICKER)
        }
    }

    private fun getBookingsByDate(onlyAvailable: Boolean = binding.cbBooking.isChecked){
        viewModel.getBookingsByDate(Date().time.toString(), onlyAvailable)
    }

    private fun setDateText(){
        binding.tvDateBooking.text = dateShortFormatString(selectedDate)
    }

    private fun initRecyclerView() {
        bookingDetailsAdapter = BookingDetailsAdapter(mutableListOf(), this)
        val linearLayoutManager = getLinearLayoutManager()
        with(binding.rvBooking) {
            layoutManager = linearLayoutManager
            adapter = bookingDetailsAdapter
            //addItemDecoration(getDividerItemDecoration(linearLayoutManager))
        }
    }

    private fun setUpPickers(
        startDate: Long = MaterialDatePicker.todayInUtcMilliseconds()
    ) {
        datePicker = getDatePicker(
            SELECT_DATE,
            startDate
        )

        datePicker.addOnPositiveButtonClickListener {
            selectedDate = it + setOneDays()
            setDateText()
            getBookingsByDate()
        }
    }

    private fun setOneDays() = 86400000

    private fun getLinearLayoutManager() = LinearLayoutManager(requireContext())

    private fun getDividerItemDecoration(linearLayoutManager: LinearLayoutManager) =
        DividerItemDecoration(
            requireContext(),
            linearLayoutManager.orientation
        )

    private fun handleUiBookings(uiState: DataState<MutableList<BookingDetails>>) {
        when (uiState) {
            is DataState.Success<MutableList<BookingDetails>> -> {
                bookingsOrigin = uiState.data
                bookings = uiState.data
                bookingDetailsAdapter.addNewList(bookingsOrigin as MutableList<Any>)
                handlerErrorVisibility(false)
                handlerProgressBarVisibility(false)
                handlerRecyclerVisibility(true)
            }
            is DataState.Error -> {
                handlerErrorVisibility(true)
                handlerProgressBarVisibility(false)
                handlerRecyclerVisibility(false)
            }
            is DataState.Loading -> {
                handlerErrorVisibility(false)
                handlerProgressBarVisibility(true)
                handlerRecyclerVisibility(false)
            }
            is DataState.Idle -> Unit
        }
    }

    private fun handlerProgressBarVisibility(show: Boolean) {
        with(binding) {
            iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun handlerRecyclerVisibility(show: Boolean) {
        with(binding) {
            clContainer.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun handlerErrorVisibility(show: Boolean) {
        with(binding) {
            iGenericError.clGenericError.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onClick(id: String) {
        //TODO: go to details
    }

    override fun onMenuClickEdit(id: String) {
        openBookingFragmentDialog(id)
    }

    override fun onMenuClickDelete(id: String) {
        showDialog(
            requireContext(), getString(R.string.delete_booking_message_dialog), getString(
                R.string.accept_title
            ), getString(R.string.cancel_title)
        ) {
            deleteBooking(id)
        }
    }

    private fun deleteBooking(id: String) {
        viewModel.deleteBooking(id)
    }

    private fun openBookingFragmentDialog(id: String = "") {
        BookingDialogFragment.newInstance(id).show(
            parentFragmentManager,
            BookingDialogFragment.BOOKING_DIALOG_FRAGMENT_FLAG
        )
    }

    companion object {
        const val SELECT_DATE = "Selecciona fecha de inicio"
        const val DATE_PICKER = "date_picker"
    }
}