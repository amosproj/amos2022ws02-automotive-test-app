package com.amos.infotaimos

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

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
        val actionBar = getSupportActionBar()
        if (actionBar != null) {
            // actionBar.setHomeAsUpIndicator(R.drawable.mybutton)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun displayToast(text: String) {
        val toastCard = findViewById<CardView>(R.id.custom_toast)
        val textView = toastCard.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = text
        toastCard
            .animate()
            .alpha(1f)
            .setDuration(500)
            .setInterpolator(DecelerateInterpolator())
            .withStartAction {
                toastCard.visibility = View.VISIBLE
            }
            .withEndAction {
                toastCard.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setInterpolator(
                    AccelerateInterpolator()
                ).withEndAction {
                    toastCard.visibility = View.GONE
                }.start()
            }
            .start()
    }
}
