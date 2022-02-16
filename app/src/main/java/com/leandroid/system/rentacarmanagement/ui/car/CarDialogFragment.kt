package com.leandroid.system.rentacarmanagement.ui.car

import android.content.DialogInterface
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
    //TODO: adicionar el color adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
        viewModel = ViewModelProvider(requireActivity(), CarViewModelFactory(repository))[CarViewModel::class.java]
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
        setUpSpinnerAdapter()
        setUpObserverViewModel()

        binding.btnClosed.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            getCar("")
            carDTO.observe(requireActivity()) { state ->
               handleUiCar(state)
            }
        }
    }

    private fun setUpUI() {
        with(binding){
            spBrand.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val brand = brandAdapter.getBrandForPosition(position)
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
                    //TODO: aca obtener el color por position desde el adapter
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
       //TODO: aca init color adapter
    }

    private fun handleUiCar(uiState: DataState<CarDTO>) {
        when (uiState) {
            is DataState.Success<CarDTO> -> {
                brandAdapter.setBrands(uiState.data.brands)
                //TODO: aca pasar los colors para el adapter
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    companion object{
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