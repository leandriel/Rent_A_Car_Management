package com.leandroid.system.rentacarmanagement.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.dto.CarDTO
import com.leandroid.system.rentacarmanagement.data.repository.CarRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentCarDialogBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.utils.DataState

class CarDialogFragment : DialogFragment() {
    private var _binding: FragmentCarDialogBinding? = null
    private val binding get() = _binding!!
    private val repository = CarRepositoryImpl(CarDataSourceImpl())
    private lateinit var viewModel: CarViewModel
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var colorAdapter: ColorAdapter
    private var car = Car()
    private var carId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
        viewModel = ViewModelProvider(
            requireActivity(),
            CarViewModelFactory(repository)
        )[CarViewModel::class.java]
        getBundleData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(
            R.style.DialogAnimation
        )

        setUpUI()
        setUpListener()
        setUpSpinnerAdapter()
        setUpObserverViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCar(carId)
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            carDTO.observe(requireActivity()) { state ->
                handleUiCar(state)
            }
        }
    }

    private fun setUpListener() {
        with(binding) {
            btnClosed.setOnClickListener {
                dismiss()
            }
            btnActions.setOnClickListener {
               setModelToCar()
               setPatentToCar()
               setCommentToCar()
                if(!car.isRequiredEmptyData){
                    viewModel.saveCar(car)
                }
            }
        }
    }

    private fun setModelToCar() {
        binding.edModel.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireContext().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                car.model = it
            }
        }
    }

    private fun setPatentToCar() {
        binding.edPatent.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireContext().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                car.patent = it
            }
        }
    }

    private fun setCommentToCar() {
        binding.edComment.let { ed ->
            ed.text.toString().let {
                car.comment = it
            }
        }
    }

    private fun getBundleData(){
        arguments?.let {
            carId = it.getString(CAR_ID_KEY, "");
        }
    }
    private fun setUpUI() {
        with(binding) {
            spBrand.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        brandAdapter.getBrandForPosition(position).let {
                            car.brand = it
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
            spColor.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        colorAdapter.getColorForPosition(position).let {
                            car.color = it
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
    }

    private fun setUpSpinnerAdapter() {
        brandAdapter = BrandAdapter(requireContext(), R.layout.simple_spinner_standar_item).also {
            binding.spBrand.adapter = it
        }
        colorAdapter = ColorAdapter(requireContext(), R.layout.simple_spinner_standar_item).also {
            binding.spColor.adapter = it
        }
    }

    private fun handleUiCar(uiState: DataState<CarDTO>) {
        when (uiState) {
            is DataState.Success<CarDTO> -> {
                brandAdapter.setBrands(uiState.data.brands)
                colorAdapter.setColors(uiState.data.colors)
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

    companion object {
        fun newInstance(id: String): CarDialogFragment {
            Bundle().apply {
                putString(CAR_ID_KEY, id);
            }.also { b ->
                return CarDialogFragment().apply {
                    arguments = b
                }
            }
        }

        const val CAR_ID_KEY = "car_id_key"
        const val CAR_DIALOG_FRAGMENT_FLAG = "car_dialog_fragment_flag"
    }
}