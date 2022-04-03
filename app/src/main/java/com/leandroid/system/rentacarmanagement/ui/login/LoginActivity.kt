package com.leandroid.system.rentacarmanagement.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.BuildConfig
import com.leandroid.system.rentacarmanagement.BuildConfig.SERVER_URL
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.api.service.LoginService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.LoginDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.LoginRepository
import com.leandroid.system.rentacarmanagement.data.repository.LoginRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.ActivityLoginBinding
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.home.HomeActivity
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils
import com.leandroid.system.rentacarmanagement.ui.utils.DataState

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = LoginRepositoryImpl(
            LoginDataSourceImpl(
                SharedPreferencesImpl(this), LoginService(ConnectivityInterceptorImpl(this), com.leandroid.system.rentacarmanagement.BuildConfig.SERVER_URL)
            )
        )
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
            userSaved.observe(this@LoginActivity) { saved ->
                saved.getContentIfNotHandled().let {
                    if (it == null || !it) {
                        handlerProgressBarVisibility(false)
                        handlerContainerVisibility(true)
                        showToast(getString(R.string.save_user_error))
                    } else {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        ComponentUtils.showToast(this, message)
    }

    private fun handleUiUser(uiState: DataState<User>) {
        when (uiState) {
            is DataState.Success<User> -> {
                saveUser(uiState.data)
            }
            is DataState.Error -> {
                handlerProgressBarVisibility(false)
                handlerContainerVisibility(true)
                showToast(getString(R.string.invalid_credentials_error))
            }
            is DataState.Loading -> {
                handlerProgressBarVisibility(true)
                handlerContainerVisibility(false)
            }
            is DataState.Idle -> Unit
        }
    }

    private fun saveUser(user: User) {
        viewModel.saveUser(user)
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

    private fun setUpListener() {
        with(binding) {
            login.setOnClickListener {
                if (isEmailEmpty()) {
                    etUsername.error = getString(R.string.required_data_error)
                } else if (isPassEmpty()) {
                    etPassword.error = getString(R.string.required_data_error)
                } else {
                    viewModel.doLogin(etUsername.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun isEmailEmpty() = binding.etUsername.text.toString().isEmpty()

    private fun isPassEmpty() = binding.etPassword.text.toString().isEmpty()
}