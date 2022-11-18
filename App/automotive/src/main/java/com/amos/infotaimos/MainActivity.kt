package com.amos.infotaimos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

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
    // private val navViewModel: NavigationPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationObserver = Observer<Boolean> { navIsActive ->
            // Update the UI
            if (::navActiveIndicatorMenuItem.isInitialized && ::navNotActiveIndicatorMenuItem.isInitialized) {
                if (navIsActive) {
                    navActiveIndicatorMenuItem.isVisible = true
                    navNotActiveIndicatorMenuItem.isVisible = false
                } else {
                    navActiveIndicatorMenuItem.isVisible = false
                    navNotActiveIndicatorMenuItem.isVisible = true
                }
            }
        }
        viewModel.navIndicatorLiveData.observe(this, navigationObserver)
    }

    fun goToNavigation() {
        val currentFragment = navController.currentDestination?.id
        if (currentFragment != R.id.navigationPage)
            navController.navigate(R.id.navigationPage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.navigation_active_indicator -> goToNavigation()
            R.id.navigation_not_active_indicator -> goToNavigation()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu)
        navActiveIndicatorMenuItem = menu.findItem(R.id.navigation_active_indicator)
        navNotActiveIndicatorMenuItem = menu.findItem(R.id.navigation_not_active_indicator)
        viewModel.updateNavigationLiveData(applicationContext)

        return super.onCreateOptionsMenu(menu)
    }

    fun displayToast(text: String) {
        val toastCard = findViewById<CardView>(R.id.custom_toast)
        val textView = toastCard.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = text
        toastCard
            .animate()
            .alpha(1f)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .withStartAction {
                toastCard.visibility = View.VISIBLE
            }
            .withEndAction {
                toastCard.animate()
                    .alpha(0f)
                    .setDuration(10000)
                    .setInterpolator(
                        AccelerateInterpolator()
                    ).withEndAction {
                        toastCard.visibility = View.GONE
                    }.start()
            }
            .start()
    }
}
