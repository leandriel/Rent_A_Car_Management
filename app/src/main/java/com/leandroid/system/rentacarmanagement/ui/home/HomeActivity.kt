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

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val homeCarViewModel: HomeCarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        setUpNavigation()

//        binding.appBarHome.fab.setOnClickListener { view ->
//            navController.navigate(R.id.nav_home)  //2
//
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.appBarHome.toolbar)
    }

    private fun setUpNavigation(){
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        navController.addOnDestinationChangedListener { _, destination, _ ->  //3
            if (destination.id == R.id.nav_home) {
                binding.appBarHome.fab.visibility = View.GONE
            } else {
                binding.appBarHome.fab.visibility = View.VISIBLE
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_car, R.id.nav_slideshow
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
        when(item.itemId){
            R.id.action_create -> homeCarViewModel.create()
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
             homeCarViewModel.setSearchText(text)
         }
        return false
    }
}