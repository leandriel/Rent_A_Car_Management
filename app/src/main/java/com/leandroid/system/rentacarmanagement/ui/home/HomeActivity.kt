package com.leandroid.system.rentacarmanagement.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.data.api.service.HomeService
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptorImpl
import com.leandroid.system.rentacarmanagement.data.datasource.HomeDataSourceImpl
import com.leandroid.system.rentacarmanagement.data.repository.HomeRepository
import com.leandroid.system.rentacarmanagement.data.repository.HomeRepositoryImpl
import com.leandroid.system.rentacarmanagement.data.utils.SharedPreferencesImpl
import com.leandroid.system.rentacarmanagement.databinding.ActivityHomeBinding
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.login.LoginActivity
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.showToast
import com.leandroid.system.rentacarmanagement.ui.utils.DataState
import com.leandroid.system.rentacarmanagement.ui.utils.FragmentEnum

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val communicationViewModel: CommunicationViewModel by viewModels()
    private var fragmentEnum: FragmentEnum = FragmentEnum.BOOKING
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: HomeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = HomeRepositoryImpl(
            HomeDataSourceImpl(
                SharedPreferencesImpl(this), HomeService(
                    ConnectivityInterceptorImpl(this), "https://change"
                )
            )
        )
        setUpToolbar()
        setUpNavigation()
        setUpViewModel()
        setUpObserverViewModel()
        setUpListener()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarHome.toolbar)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(repository)
        )[HomeViewModel::class.java]
    }

    private fun setUpObserverViewModel() {
        with(viewModel) {
            getUser()
            user.observe(this@HomeActivity) { state ->
                handleUiUser(state)
            }
        }
    }

    private fun handleUiUser(uiState: DataState<User?>) {
        when (uiState) {
            is DataState.Success<User?> -> {
                handlerProgressBarVisibility(false)
                handlerDrawerVisibility(true)
                uiState.data?.let {
                    binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_company_name).text = it.company.name
                    binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_name).text = it.userName
                    //TODO: Implement Glide to campany image

                    communicationViewModel.user = it
                } ?: kotlin.run {
                    startActivity(Intent(this, LoginActivity::class.java))
                    showToast(this, getString(R.string.unknown_error_text))
                    finish()
                }
            }
            is DataState.Error -> {
                startActivity(Intent(this, LoginActivity::class.java))
                showToast(this, getString(R.string.unknown_error_text))
                finish()
            }
            is DataState.Loading -> {
                handlerProgressBarVisibility(true)
                handlerDrawerVisibility(false)
            }
            is DataState.Idle -> Unit
        }
    }

    private fun handlerProgressBarVisibility(show: Boolean) {
        with(binding) {
            iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun handlerDrawerVisibility(show: Boolean) {
        with(binding) {
            drawerLayout.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun setUpListener() {
//        binding.appBarHome.fab.setOnClickListener { view ->
//           getDatePicker()
//        }
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        navController.addOnDestinationChangedListener { _, destination, _ ->  //3
            fragmentEnum = when(destination.id) {
                R.id.nav_booking -> FragmentEnum.BOOKING
                R.id.nav_car -> FragmentEnum.CAR
                else -> FragmentEnum.USER
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_booking, R.id.nav_car, R.id.nav_user
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        val menuItem = menu.findItem(R.id.action_search)
        (menuItem.actionView as SearchView).apply {
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(this@HomeActivity)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_create -> {
                when (fragmentEnum) {
                    FragmentEnum.CAR -> communicationViewModel.createCar()
                    FragmentEnum.BOOKING -> communicationViewModel.createBooking()
                    else -> communicationViewModel.createUser()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onQueryTextSubmit(text: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let {
            communicationViewModel.setSearchText(text)
        }
        return false
    }
}