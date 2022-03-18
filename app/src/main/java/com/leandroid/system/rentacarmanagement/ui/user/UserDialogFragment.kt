package com.leandroid.system.rentacarmanagement.ui.user

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.api.service.UserService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.UserDataSourceImpl
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
            user.observe(this@UserDialogFragment) { state ->
                handleUiUser(state)
            }
            saveSuccess.observe(this@UserDialogFragment) { success ->
                success.getContentIfNotHandled()?.let {
                    if (it) {
                        cleanComponents()
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.car_added_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.car_added_error)
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
                            requireActivity().getString(R.string.car_updated_success)
                        )
                    } else {
                        showToast(
                            requireActivity(),
                            requireActivity().getString(R.string.car_updated_error)
                        )
                    }
                }
            }
        }
    }

    private fun cleanComponents() {
        with(binding) {
            edModel.setText(EMPTY_STRING)
            edPatent.setText(EMPTY_STRING)
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
                setModelToUser()
                setPatentToUser()
                setCommentToUser()
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

    private fun setModelToUser() {
        binding.edModel.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                user.model = it
            }
        }
    }

    private fun setPatentToUser() {
        binding.edPatent.let { ed ->
            ed.text.toString().let {
                it.ifEmpty {
                    ed.error = requireActivity().getString(R.string.required_data_error)
                    return@let
                }
                ed.error = null
                user.patent = it
            }
        }
    }

    private fun setCommentToUser() {
        binding.edComment.let { ed ->
            ed.text.toString().let {
                user.comment = it
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
//            spBrand.apply {
//                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        brandAdapter.getBrandForPosition(position).let {
//                            car.brand = it
//                        }
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//                }
//            }
//            spColor.apply {
//                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        colorAdapter.getColorForPosition(position).let {
//                            car.color = it
//                        }
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//                }
//            }
        }
    }

    private fun setUpSpinnerAdapter() {
//        brandAdapter = BrandAdapter(requireActivity(), R.layout.simple_spinner_standar_item).also {
//            binding.spBrand.adapter = it
//        }
//        colorAdapter = ColorAdapter(requireActivity(), R.layout.simple_spinner_standar_item).also {
//            binding.spColor.adapter = it
//        }
    }

    private fun handleUiUser(uiState: DataState<UserDTO>) {
        when (uiState) {
            is DataState.Success<UserDTO> -> {
                //brandAdapter.setBrands(uiState.data.brands)
                //colorAdapter.setColors(uiState.data.colors)
                if (!isCreate) {
                    user = uiState.data.car
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
           // spBrand.setSelection(brandAdapter.getPositionByBrand(car.brand))
           // spColor.setSelection(colorAdapter.getPositionByColor(car.color))
            edModel.setText(user.model)
            edPatent.setText(user.patent)
            edComment.setText(user.comment)
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