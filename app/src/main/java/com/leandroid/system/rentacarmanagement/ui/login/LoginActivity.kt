package com.leandroid.system.rentacarmanagement.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.datasource.LoginDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.LoginRepositoryImpl
import com.leandroid.system.rentacarmanagement.databinding.ActivityLoginBinding
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.home.HomeActivity
import com.leandroid.system.rentacarmanagement.ui.utils.DataState

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private val repository = LoginRepositoryImpl(LoginDataSourceImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpObserver()
        setUpListener()
    }

    private fun setUpViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                LoginViewModelFactory(repository)
            )[LoginViewModel::class.java]
    }

    private fun setUpObserver() {
        with(viewModel) {
            user.observe(this@LoginActivity) { state ->
                handleUiUser(state)
            }
        }
    }

    private fun handleUiUser(uiState: DataState<User>) {
        when (uiState) {
            is DataState.Success<User> -> {
                saveUser(uiState.data)
                handlerErrorVisibility(false)
                handlerProgressBarVisibility(false)
                handlerContainerVisibility(true)
                startActivity(Intent(this, HomeActivity::class.java))
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

    private fun saveUser(user: User){

    }
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

    private fun setUpListener() {
        with(binding) {
            login.setOnClickListener {
                if (isEmailEmpty()) {
                   username.error = getString(R.string.required_data_error)
                } else if (isPassEmpty()) {
                    password.error = getString(R.string.required_data_error)
                } else {
                    viewModel.doLogin(username.text.toString(), password.toString())
                }
            }
        }
    }

    private fun isEmailEmpty() = binding.username.text.toString().isEmpty()

    private fun isPassEmpty() = binding.password.text.toString().isEmpty()
}