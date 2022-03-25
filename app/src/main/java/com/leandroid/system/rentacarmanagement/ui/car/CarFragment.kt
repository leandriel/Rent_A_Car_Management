package com.leandroid.system.rentacarmanagement.ui.car

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
import com.leandroid.system.rentacarmanagement.data.api.service.CarService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.CarRepository
import com.leandroid.system.rentacarmanagement.data.repository.CarRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentCarBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.car.CarDialogFragment.Companion.CAR_DIALOG_FRAGMENT_FLAG
import com.leandroid.system.rentacarmanagement.ui.home.CommunicationViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showDialog
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class CarFragment : Fragment(), RecyclerListener {
    private lateinit var binding: FragmentCarBinding
    private lateinit var carAdapter: CarAdapter
    private lateinit var repository: CarRepository
    private lateinit var viewModel: CarViewModel
    private val communicationViewModel: CommunicationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = CarRepositoryImpl(
            CarDataSourceImpl(
                SharedPreferencesImpl(requireContext()), CarService(
                    ConnectivityInterceptorImpl(requireContext()), "https://change"
                )
            )
        )
        setUpViewModel()
        setUpObserverViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCars()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            CarViewModelFactory(repository)
        )[CarViewModel::class.java]
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            cars.observe(this@CarFragment) { state ->
                handleUiCars(state)
            }
        }
        with(communicationViewModel) {
            searchText.observe(this@CarFragment) { text ->
                carAdapter.filterByBrand(text)
            }

            isCreateCar.observe(this@CarFragment) { isCreate ->
                isCreate.getContentIfNotHandled()?.let {
                    if (it) {
                        openCarFragmentDialog()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        carAdapter = CarAdapter(this)
        val linearLayoutManager = getLinearLayoutManager()
        with(binding.rvCar) {
            layoutManager = linearLayoutManager
            adapter = carAdapter
            addItemDecoration(getDividerItemDecoration(linearLayoutManager))
        }
    }

    private fun getLinearLayoutManager() = LinearLayoutManager(requireContext())

    private fun getDividerItemDecoration(linearLayoutManager: LinearLayoutManager) =
        DividerItemDecoration(
            requireContext(),
            linearLayoutManager.orientation
        )

    private fun handleUiCars(uiState: DataState<MutableList<Car>>) {
        when (uiState) {
            is DataState.Success<MutableList<Car>> -> {
                carAdapter.setCars(uiState.data)
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
            rvCar.visibility = if (show) View.VISIBLE else View.GONE
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
        openCarFragmentDialog(id)
    }

    override fun onMenuClickDelete(id: String) {
        showDialog(
            requireContext(), getString(R.string.delete_car_message_dialog), getString(
                R.string.accept_title
            ), getString(R.string.cancel_title)
        ) {
            deleteCar(id)
        }
    }

    private fun deleteCar(id: String) {
        viewModel.deleteCar(id)
    }

    private fun openCarFragmentDialog(id: String = "") {
        CarDialogFragment.newInstance(id, communicationViewModel.user.type.code == 3).show(parentFragmentManager, CAR_DIALOG_FRAGMENT_FLAG)
    }
}
