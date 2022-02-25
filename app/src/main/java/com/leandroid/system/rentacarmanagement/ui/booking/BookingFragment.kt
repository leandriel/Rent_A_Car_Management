package com.leandroid.system.rentacarmanagement.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.BookingDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentBookingBinding
import com.leandroid.system.rentacarmanagement.model.BookingDetails
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.car.CarAdapter
import com.leandroid.system.rentacarmanagement.ui.car.CarViewModel
import com.leandroid.system.rentacarmanagement.ui.car.CarViewModelFactory
import com.leandroid.system.rentacarmanagement.ui.home.CommunicationViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class BookingFragment : Fragment(), RecyclerListener {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var bookingDetailsAdapter: BookingDetailsAdapter
    private val repository = BookingRepositoryImpl(BookingDataSourceImpl())
    private lateinit var viewModel: BookingViewModel
    private val communicationViewModel: CommunicationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bookingDetailsAdapter = BookingDetailsAdapter(mutableListOf(), this)
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
        initRecyclerView()
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProvider(
            requireActivity(),
            BookingViewModelFactory(repository)
        )[BookingViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBookingsByDate("")
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            bookingsDetails.observe(requireActivity()) { state ->
                handleUiBookings(state)
            }
        }
        with(communicationViewModel){
            searchText.observe(requireActivity()) { text ->
               // bookingDetailsAdapter.filterByBrand(text)
            }

            isCreateBooking.observe(requireActivity()) { isCreate ->
                isCreate.getContentIfNotHandled()?.let {
                    if(it){
                        //openCarFragmentDialog()
                    }
                }

            }
        }
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

    private fun getLinearLayoutManager() = LinearLayoutManager(requireContext())

    private fun getDividerItemDecoration(linearLayoutManager: LinearLayoutManager) =
        DividerItemDecoration(
            requireContext(),
            linearLayoutManager.orientation
        )

    private fun handleUiBookings(uiState: DataState<MutableList<BookingDetails>>) {
        when (uiState) {
            is DataState.Success<MutableList<BookingDetails>> -> {
                bookingDetailsAdapter.addNewList(uiState.data as MutableList<Any>)
                //bookingDetailsAdapter.setBookings(uiState.data)
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

    override fun onMenuClickEdit(position: Int) {
        //val bookingDetails = bookingDetailsAdapter.getItemByPosition(position)
        //openCarFragmentDialog(car.id)
    }

    override fun onMenuClickDelete(position: Int) {
       // val bookingDetails = bookingDetailsAdapter.getItemByPosition(position)
        ComponentUtils.showDialog(requireContext(), getString(R.string.delete_car_message_dialog), getString(
            R.string.accept_title), getString(R.string.cancel_title)
        ) {
            //deleteBooking(bookingDetails.id)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}