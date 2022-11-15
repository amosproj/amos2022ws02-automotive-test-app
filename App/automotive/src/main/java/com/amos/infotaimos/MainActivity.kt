package com.amos.infotaimos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.amos.infotaimos.model.NavigationService

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navActiveIndicatorMenuItem: MenuItem
    private lateinit var navNotActiveIndicatorMenuItem: MenuItem

    private val viewModel: MainActivityViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainActivityViewModel(applicationContext) as T
            }
        }
    }
    private val navViewModel: NavigationPageViewModel by viewModels()

    fun updateNavIndicatorInActionBar(navIsActive : Boolean){
        if (navIsActive){
            navActiveIndicatorMenuItem.isVisible = true
            navNotActiveIndicatorMenuItem.isVisible = false
        }else{
            navActiveIndicatorMenuItem.isVisible = false
            navNotActiveIndicatorMenuItem.isVisible = true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val actionBar = supportActionBar
        if (actionBar != null) {
            // actionBar.setHomeAsUpIndicator(R.drawable.mybutton)
            actionBar.setDisplayHomeAsUpEnabled(true)
            // actionBar.setIcon(R.drawable.navigation_active)
            // actionBar.setLogo(R.drawable.navigation_not_active)
            // actionBar.setDisplayUseLogoEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu)
        // navViewModel.setNavMenuItems( menu!!.findItem(R.id.navigation_active_indicator), menu.findItem(R.id.navigation_not_active_indicator))
        navActiveIndicatorMenuItem = menu.findItem(R.id.navigation_active_indicator)
        navNotActiveIndicatorMenuItem = menu.findItem(R.id.navigation_not_active_indicator)
        navViewModel.setNavMenuItems(navActiveIndicatorMenuItem, navNotActiveIndicatorMenuItem)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.navigation_active_indicator)?.isVisible = false
        menu?.findItem(R.id.navigation_not_active_indicator)?.isVisible = false

        return true
    }
}
