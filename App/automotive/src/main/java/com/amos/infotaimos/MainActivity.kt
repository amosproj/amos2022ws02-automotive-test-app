package com.amos.infotaimos

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.amos.infotaimos.model.MediaService
import com.amos.infotaimos.model.NotificationManager
import com.amos.infotaimos.model.TimerService

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

        if (checkSelfPermission(RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(RECORD_AUDIO), REQ_PERM_CODE)
        }else
            viewModel.setupSpeechService(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(POST_NOTIFICATIONS), REQ_PERM_CODE)
            }
        } else {
            viewModel.setupNotificationChannel(this)
        }
        viewModel.navIndicatorLiveData.observe(this, navigationObserver)
        startService(Intent(this, TimerService::class.java))
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

    fun displayToast(text: String, durationStart: Long, durationEnd: Long) {
        val toastCard = findViewById<CardView>(R.id.custom_toast)
        val textView = toastCard.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = text
        toastCard
            .animate()
            .alpha(1f)
            .setDuration(durationStart)
            .setInterpolator(DecelerateInterpolator())
            .withStartAction {
                toastCard.visibility = View.VISIBLE
            }
            .withEndAction {
                toastCard.animate()
                    .alpha(0f)
                    .setDuration(durationEnd)
                    .setInterpolator(
                        AccelerateInterpolator()
                    ).withEndAction {
                        toastCard.visibility = View.GONE
                    }.start()
            }
            .start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionRequestIndex = permissions.indexOf(POST_NOTIFICATIONS)
            if (notificationPermissionRequestIndex != -1 && grantResults[notificationPermissionRequestIndex] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setupNotificationChannel(this)
            }
        }
        val recordAudioPermissionRequestIndex = permissions.indexOf(RECORD_AUDIO)
        if (recordAudioPermissionRequestIndex != -1 && grantResults[recordAudioPermissionRequestIndex] == PackageManager.PERMISSION_DENIED) {
            viewModel.setupSpeechService(true)
        } else
            viewModel.setupSpeechService(false)

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val REQ_PERM_CODE = 42
    }
}
