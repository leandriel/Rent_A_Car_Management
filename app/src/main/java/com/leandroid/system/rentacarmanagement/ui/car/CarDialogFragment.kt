package com.leandroid.system.rentacarmanagement.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.CarDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.CarRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.FragmentCarDialogBinding

class CarDialogFragment : DialogFragment() {
    private var _binding: FragmentCarDialogBinding? = null
    private val binding get() = _binding!!
    private val repository = CarRepositoryImpl(CarDataSourceImpl())
    private lateinit var viewModel: CarViewModel

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

        binding.btnClosed.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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