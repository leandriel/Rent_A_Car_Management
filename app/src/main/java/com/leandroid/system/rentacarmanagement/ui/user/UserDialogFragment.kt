package com.leandroid.system.rentacarmanagement.ui.user

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.api.service.UserService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.UserDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.dto.UserDTO
import com.leandroid.system.rentacarmanagement.data.repository.UserRepository
import com.leandroid.system.rentacarmanagement.data.repository.UserRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentUserDialogBinding
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showToast
import com.leandroid.system.rentacarmanagement.ui.utils.DataState

class UserDialogFragment : DialogFragment() {
    private var _binding: FragmentUserDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel
    private lateinit var userTypeAdapter: UserTypeAdapter
    private var user = User()
    private var userId = ""
    private val isCreate: Boolean
        get() = userId.isEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
        repository = UserRepositoryImpl(
            UserDataSourceImpl(
                SharedPreferencesImpl(requireContext()), UserService(
                    ConnectivityInterceptorImpl(requireContext()), "https://change"
                )
            )
        )
        setUpViewModel()
        getBundleData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        _binding = FragmentUserDialogBinding.inflate(inflater, container, false)
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
        viewModel.getUser(userId)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            UserViewModelFactory(repository)
        )[UserViewModel::class.java]
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            userDTO.observe(this@UserDialogFragment) { state ->
                handleUiUser(state)
            }
            saveSuccess.observe(this@UserDialogFragment) { success ->
                success.getContentIfNotHandled()?.let {
                    if (it) {
                        cleanComponents()
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.user_added_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.user_added_error)
                        )
                    }
                }
            }

            updateSuccess.observe(this@UserDialogFragment) { success ->
                success.getContentIfNotHandled()?.let {
                    if (it) {
                        cleanComponents()
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.user_updated_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.user_updated_error)
                        )
                    }
                }
            }
        }
    }

    private fun cleanComponents() {
        with(binding) {
            edUserName.setText(EMPTY_STRING)
            edUserName.setText(EMPTY_STRING)
            btnActions.text = requireActivity().getString(R.string.create_title)
        }
    }

    private fun setUpListener() {
        with(binding) {
            btnClosed.setOnClickListener {
                dismiss()
            }
            btnActions.setOnClickListener {
                setUserNameToUser()
                setPassToUser()
                if (!user.isRequiredEmptyData) {
                    if (isCreate)
                        viewModel.saveUser(user)
                    else
                        viewModel.updateUser(user)
                } else {
                    showToast(
                        requireActivity(),
                        requireActivity().getString(R.string.required_datas_error)
                    )
                }
            }
        }
    }

    private fun setUserNameToUser() {
        binding.edUserName.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                user.userName = it
            }
        }
    }

    private fun setPassToUser() {
        binding.edPass.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                user.password = it
            }
        }
    }

    private fun getBundleData() {
        arguments?.let {
            userId = it.getString(USER_ID_KEY, EMPTY_STRING)
        }
    }

    private fun setUpUI() {
        with(binding) {
            spType.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        userTypeAdapter.getUserTypeForPosition(position).let {
                            user.type = it
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
    }

    private fun setUpSpinnerAdapter() {
        userTypeAdapter = UserTypeAdapter(requireActivity(), R.layout.simple_spinner_standar_item).also {
            binding.spType.adapter = it
        }
    }

    private fun handleUiUser(uiState: DataState<UserDTO>) {
        when (uiState) {
            is DataState.Success<UserDTO> -> {
                userTypeAdapter.setUserTypes(uiState.data.types)
                if (!isCreate) {
                    user = uiState.data.user
                    setUserUI()
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

    private fun setUserUI() {
        with(binding) {
            btnActions.text = requireActivity().getText(R.string.update_title)
            spType.setSelection(userTypeAdapter.getPositionByUserType(user.type))
            edUserName.setText(user.userName)
            edPass.setText(user.password)
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
        fun newInstance(id: String): UserDialogFragment {
            Bundle().apply {
                putString(USER_ID_KEY, id);
            }.also { b ->
                return UserDialogFragment().apply {
                    arguments = b
                }
            }
        }

        const val USER_ID_KEY = "user_id_key"
        const val USER_DIALOG_FRAGMENT_FLAG = "user_dialog_fragment_flag"
        const val EMPTY_STRING = ""
    }
}