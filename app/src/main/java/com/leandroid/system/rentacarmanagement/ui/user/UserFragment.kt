package com.leandroid.system.rentacarmanagement.ui.user

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
import com.leandroid.system.rentacarmanagement.data.api.service.UserService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.UserDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.UserRepository
import com.leandroid.system.rentacarmanagement.data.repository.UserRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentUserBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.car.CarDialogFragment.Companion.CAR_DIALOG_FRAGMENT_FLAG
import com.leandroid.system.rentacarmanagement.ui.home.CommunicationViewModel
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showDialog
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class UserFragment : Fragment(), RecyclerListener {
    private lateinit var binding: FragmentUserBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel
    private val communicationViewModel: CommunicationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = UserRepositoryImpl(
            UserDataSourceImpl(
                SharedPreferencesImpl(requireContext()), UserService(
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
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsers()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            UserViewModelFactory(repository)
        )[UserViewModel::class.java]
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            users.observe(this@UserFragment) { state ->
                handleUiUsers(state)
            }
        }
        with(communicationViewModel) {
            searchText.observe(this@UserFragment) { text ->
                userAdapter.filterByBrand(text)
            }

            isCreateCar.observe(this@UserFragment) { isCreate ->
                isCreate.getContentIfNotHandled()?.let {
                    if (it) {
                        openCarFragmentDialog()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(this)
        val linearLayoutManager = getLinearLayoutManager()
        with(binding.rvUser) {
            layoutManager = linearLayoutManager
            adapter = userAdapter
            addItemDecoration(getDividerItemDecoration(linearLayoutManager))
        }
    }

    private fun getLinearLayoutManager() = LinearLayoutManager(requireContext())

    private fun getDividerItemDecoration(linearLayoutManager: LinearLayoutManager) =
        DividerItemDecoration(
            requireContext(),
            linearLayoutManager.orientation
        )

    private fun handleUiUsers(uiState: DataState<MutableList<User>>) {
        when (uiState) {
            is DataState.Success<MutableList<User>> -> {
                userAdapter.setUsers(uiState.data)
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
            rvUser.visibility = if (show) View.VISIBLE else View.GONE
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
            requireContext(), getString(R.string.delete_user_message_dialog), getString(
                R.string.accept_title
            ), getString(R.string.cancel_title)
        ) {
            deleteUser(id)
        }
    }

    private fun deleteUser(id: String) {
        viewModel.deleteUser(id)
    }

    private fun openCarFragmentDialog(id: String = "") {
        UserDialogFragment.newInstance(id).show(parentFragmentManager, CAR_DIALOG_FRAGMENT_FLAG)
    }
}
