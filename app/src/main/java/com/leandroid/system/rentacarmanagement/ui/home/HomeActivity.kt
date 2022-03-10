package com.leandroid.system.rentacarmanagement.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ActivityHomeBinding
import com.leandroid.system.rentacarmanagement.ui.utils.ComponentUtils.getDatePicker
import com.leandroid.system.rentacarmanagement.ui.utils.FragmentEnum

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val communicationViewModel: CommunicationViewModel by viewModels()
    private var fragmentEnum: FragmentEnum = FragmentEnum.CAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        setUpNavigation()
        setUpListener()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.appBarHome.toolbar)
    }

    private fun setUpListener() {
//        binding.appBarHome.fab.setOnClickListener { view ->
//           getDatePicker()
//        }
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        navController.addOnDestinationChangedListener { _, destination, _ ->  //3
            if (destination.id == R.id.nav_booking) {
                fragmentEnum = FragmentEnum.BOOKING
            //    binding.appBarHome.fab.visibility = View.VISIBLE
            } else {
                fragmentEnum = FragmentEnum.CAR
              //  binding.appBarHome.fab.visibility = View.GONE
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_booking, R.id.nav_car
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
                    if(fragmentEnum == FragmentEnum.CAR)
                        communicationViewModel.createCar()
                    else
                        communicationViewModel.createBooking()
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