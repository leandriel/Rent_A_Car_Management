package com.leandroid.system.rentacarmanagement.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.CarRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentCarBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.ui.home.HomeCarViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils
import com.leandroid.system.rentacarmanagement.ui.utils.DataState


class CarFragment : Fragment(), CarListener {
    private lateinit var binding: FragmentCarBinding
    private lateinit var carAdapter: CarAdapter
    private val repository = CarRepositoryImpl(CarDataSourceImpl())
    private lateinit var viewModel: CarViewModel
    private val homeCarViewModel: HomeCarViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            CarViewModelFactory(repository)
        ).get(CarViewModel::class.java)
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
        //
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            getCars()
            cars.observe(requireActivity()) { state ->
                handleUiCars(state)
            }
        }
        with(homeCarViewModel){
            searchText.observe(requireActivity()) { text ->
                carAdapter.filterByBrand(text)
            }

            isCreate.observe(requireActivity()) { isCreate ->
                isCreate.getContentIfNotHandled()?.let {
                    if(it){
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
      //gotodetail
    }

    override fun onMenuClickEdit(position: Int) {
        val car = carAdapter.getItemByPosition(position)
        openCarFragmentDialog(car.id)
    }

    override fun onMenuClickDelete(position: Int) {
        val car = carAdapter.getItemByPosition(position)
        ComponentUtils.showDialog(requireContext(), getString(R.string.delete_car_message_dialog), getString(
                    R.string.accept_title), getString(R.string.cancel_title)
        ) {
            deleteCar(car.id)
        }
    }

    private fun deleteCar(id: String) {
        viewModel.deleteCar(id)
    }

    private fun openCarFragmentDialog(id: String = ""){
        CarDialogFragment.newInstance(id).show(childFragmentManager, CarDialogFragment.CAR_DIALOG_FRAGMENT_FLAG)
    }
}
