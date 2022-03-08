package com.leandroid.system.rentacarmanagement.ui.booking

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.core.util.Pair
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.BookingDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.dto.BookingDTO
import com.leandroid.system.rentacarmanagement.data.repository.BookingRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentBookingDialogBinding
import com.leandroid.system.rentacarmanagement.model.Booking
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.getRangePicker
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.getTimePicker
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showToast
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.DateTimeUtils.dateShortFormatString
import java.util.*

class BookingDialogFragment : DialogFragment() {
    private var _binding: FragmentBookingDialogBinding? = null
    private val binding get() = _binding!!
    private val repository = BookingRepositoryImpl(BookingDataSourceImpl())
    private lateinit var viewModel: BookingViewModel
    private lateinit var carAdapter: CarAdapter
    private var booking = Booking()
    private var bookingId = ""
    private val isCreate: Boolean
        get() = bookingId.isEmpty()
    private lateinit var dateRangePicker: MaterialDatePicker<Pair<Long, Long>>
    private lateinit var timePicker: MaterialTimePicker
    private var isDeliveryTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
        setUpViewModel()
        getBundleData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        _binding = FragmentBookingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(
            R.style.DialogAnimation
        )
        setUpUI()
        if (isCreate)
            setUpPickers()
        setUpListener()
        setUpSpinnerAdapter()
        setUpObserverViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBooking(bookingId)
    }

    private fun setUpPickers(
        startDate: Long = MaterialDatePicker.todayInUtcMilliseconds(),
        endDate: Long = MaterialDatePicker.todayInUtcMilliseconds() + setThreeDays()
    ) {
        dateRangePicker = getRangePicker(
            SELECT_DATES,
            startDate,
            endDate
        )

        dateRangePicker.addOnPositiveButtonClickListener {
            booking.startDate = it.first + setOneDays()
            booking.endDate = it.second + setOneDays()
            with(binding) {
                tvDate.text = booking.startEndDate
                tvDelivery.text = booking.startDateTime
                tvReturn.text = booking.endDateTime
            }
            print(it)
            // Respond to positive button click.
        }
        dateRangePicker.addOnNegativeButtonClickListener {
            print(it)
        }
        dateRangePicker.addOnCancelListener {
            print(it)
        }
        dateRangePicker.addOnDismissListener {
            print(it)
        }

        timePicker = getTimePicker(SELECT_HOUR)
        timePicker.addOnPositiveButtonClickListener {
            if (isDeliveryTime) {
                booking.deliveryTime = getTimeFormat()
                binding.tvDelivery.text = booking.startDateTime
            } else {
                booking.returnTime = getTimeFormat()
                binding.tvReturn.text = booking.endDateTime
            }
        }
        timePicker.addOnNegativeButtonClickListener {
            // call back code
        }
        timePicker.addOnCancelListener {
            // call back code
        }
        timePicker.addOnDismissListener {
            // call back code
        }

    }

    private fun setThreeDays() = setOneDays() * 3

    private fun setOneDays() = 86400000

    private fun getHour() =
        if (timePicker.hour.toString().length == 1) "0${timePicker.hour}" else timePicker.hour.toString()

    private fun getMinute() =
        if (timePicker.minute.toString().length == 1) "${timePicker.minute}0" else timePicker.minute.toString()

    private fun getTimeFormat() = getHour().plus(":").plus(getMinute())

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            BookingViewModelFactory(repository)
        )[BookingViewModel::class.java]
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            bookingDTO.observe(this@BookingDialogFragment) { state ->
                handleBookingUI(state)
            }

            saveSuccess.observe(this@BookingDialogFragment) { success ->
                success.getContentIfNotHandled()?.let {
                    if (it) {
                        cleanComponents()
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.booking_added_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.booking_added_error)
                        )
                    }
                }
            }

            updateSuccess.observe(this@BookingDialogFragment) { success ->
                success.getContentIfNotHandled()?.let {
                    if (it) {
                        cleanComponents()
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.booking_updated_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.booking_updated_error)
                        )
                    }
                }
            }
        }
    }

    private fun cleanComponents() {
        with(binding) {
            edDrivingLicence.setText(EMPTY_STRING)
            edFly.setText(EMPTY_STRING)
            edHotel.setText(EMPTY_STRING)
            edReturnPlace.setText(EMPTY_STRING)
            edPrice.setText(EMPTY_STRING)
            edCommission.setText(EMPTY_STRING)
            edComment.setText(EMPTY_STRING)
            btnActions.text = requireActivity().getString(R.string.create_title)
        }
    }

    private fun setUpListener() {
        with(binding) {
            btnClosed.setOnClickListener {
                dismiss()
            }
            btnActions.setOnClickListener {
                setFlyToBooking()
                setLicenceToBooking()
                //setStartDateToBooking()
                //setEndDateToBooking()
                setHotelToBooking()
                setCommentToBooking()
//                setReturnCarToBooking()
                if (!booking.isRequiredEmptyData) {
                    if (isCreate)
                        viewModel.saveBooking(booking)
                    else
                        viewModel.updateBooking(booking)
                } else {
                    showToast(
                        requireActivity(),
                        requireActivity().getString(R.string.required_datas_error)
                    )
                }
            }

            llDate.setOnClickListener {
                if (!dateRangePicker.isAdded)
                    dateRangePicker.show(childFragmentManager, DATA_RANGE_PICKER)
            }

            llDelivery.setOnClickListener {
                if (!timePicker.isAdded)
                    timePicker.show(childFragmentManager, TIME_PICKER)
                isDeliveryTime = true
            }

            llReturn.setOnClickListener {
                if (!timePicker.isAdded)
                    timePicker.show(childFragmentManager, TIME_PICKER)
                isDeliveryTime = false
            }
        }
    }

//    private fun setReturnCarToBooking() {
//        with(binding) {
//            tvReturn.let { ed ->
//                ed.text.toString().let {
//                    it.ifEmpty {
//                        ed.error = requireActivity().getString(R.string.required_data_error)
//                        return@let
//                    }
//                    ed.error = null
//                    booking.returnCar.dateTime = it
//                }
//            }
//            edReturnPlace.let { ed ->
//                ed.text.toString().let {
//                    it.ifEmpty {
//                        ed.error = requireActivity().getString(R.string.required_data_error)
//                        return@let
//                    }
//                    ed.error = null
//                    booking.returnCar.place = it
//                }
//            }
//        }
//    }

    private fun setFlyToBooking() {
        binding.edFly.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                booking.fly = it
            }
        }
    }

    private fun setPriceToBooking() {
        binding.edPrice.let { ed ->
            ed.text.toString().let {
                booking.price = it
            }
        }
    }

    private fun setCommissionToBooking() {
        binding.edCommission.let { ed ->
            ed.text.toString().let {
                booking.commission = it
            }
        }
    }

    private fun setHotelToBooking() {
        binding.edHotel.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                booking.hotel = it
            }
        }
    }

//    private fun setStartDateToBooking() {
//        binding.tvDate.let { ed ->
//            ed.text.toString().let {
//                it.ifEmpty {
//                    ed.error = requireActivity().getString(R.string.required_data_error)
//                    return@let
//                }
//                ed.error = null
//                booking.startDate = it
//            }
//        }
//    }

//    private fun setEndDateToBooking() {
//        binding.tvDate.let { ed ->
//            ed.text.toString().let {
//                it.ifEmpty {
//                    ed.error = requireActivity().getString(R.string.required_data_error)
//                    return@let
//                }
//                ed.error = null
//                booking.endDate = it
//            }
//        }
//    }

    private fun setLicenceToBooking() {
        binding.edDrivingLicence.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                booking.drivingLicense = it
            }
        }
    }

    private fun setCommentToBooking() {
        binding.edComment.let { ed ->
            ed.text.toString().let {
                booking.comment = it
            }
        }
    }

    private fun getBundleData() {
        arguments?.let {
            bookingId = it.getString(BOOKING_ID_KEY, EMPTY_STRING)
        }
    }

    private fun setUpUI() {
        with(binding) {
            spCar.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        carAdapter.getCarForPosition(position).let {
                            booking.car = it
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
    }

    private fun setUpSpinnerAdapter() {
        carAdapter = CarAdapter(requireActivity(), R.layout.simple_spinner_standar_item).also {
            binding.spCar.adapter = it
        }
    }

    private fun handleBookingUI(uiState: DataState<BookingDTO>) {
        when (uiState) {
            is DataState.Success<BookingDTO> -> {
                carAdapter.setCars(uiState.data.cars)

                if (!isCreate) {
                    booking = uiState.data.booking
                    setBookingUI()
                }
                handlerErrorVisibility(false)
                handlerProgressBarVisibility(false)
                handlerContainerVisibility(true)
            }
            is DataState.Error -> {
                handlerErrorVisibility(true)
                handlerProgressBarVisibility(false)
                handlerContainerVisibility(false)
            }
            is DataState.Loading -> {
                handlerErrorVisibility(false)
                handlerProgressBarVisibility(true)
                handlerContainerVisibility(false)
            }
            is DataState.Idle -> Unit
        }
    }

    private fun setBookingUI() {
        with(binding) {
            btnActions.text = requireActivity().getText(R.string.update_title)
            spCar.setSelection(carAdapter.getPositionByCar(booking.car))

            if (booking.startDate != null && booking.endDate != null)
                setUpPickers(booking.startDate!!, booking.endDate!!)
            else
                setUpPickers()

            edDrivingLicence.setText(booking.drivingLicense)
            edFly.setText(booking.fly)
            edHotel.setText(booking.hotel)
            tvDate.text = booking.startEndDate
            tvDelivery.text = booking.startDateTime
            edDeliveryPlace.setText(booking.deliveryPlace)
            tvReturn.text = booking.endDateTime
            edReturnPlace.setText(booking.returnPlace)
            edComment.setText(booking.comment)
            edPrice.setText(booking.price)
            edCommission.setText(booking.commission)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    private fun handlerProgressBarVisibility(show: Boolean) {
        with(binding) {
            iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun handlerContainerVisibility(show: Boolean) {
        with(binding) {
            clContainer.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun handlerErrorVisibility(show: Boolean) {
        with(binding) {
            iGenericError.clGenericError.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    companion object {
        fun newInstance(id: String): BookingDialogFragment {
            Bundle().apply {
                putString(BOOKING_ID_KEY, id);
            }.also { b ->
                return BookingDialogFragment().apply {
                    arguments = b
                }
            }
        }

        const val BOOKING_ID_KEY = "booking_id_key"
        const val BOOKING_DIALOG_FRAGMENT_FLAG = "booking_dialog_fragment_flag"
        const val EMPTY_STRING = ""
        const val DATA_RANGE_PICKER = "date_range_picker"
        const val TIME_PICKER = "time_picker"
        const val SELECT_DATES = "Selecciona las fechas"
        const val SELECT_HOUR = "Selecciona la hora"
    }
}