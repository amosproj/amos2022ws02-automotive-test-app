package com.amos.infotaimos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

class NavigationPage : Fragment() {

    companion object {
        fun newInstance() = NavigationPage()
    }

    private val viewModel: NavigationPageViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return context?.let { NavigationPageViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startNavButton : Button = view.findViewById(R.id.start_navigation_button)
        val delaySpinner : Spinner = view.findViewById(R.id.delay_spinner)

        startNavButton.setOnClickListener {
            //get selected Delay
            val delayString = delaySpinner.selectedItem as String
            var delay = 0L
            if (delayString == "30s")
                delay = 30000L
            else if(delayString == "1min")
                delay = 60000L
            //call startNavigation
            viewModel.startNavigation(delay)
        }
        // Set another on click listener for Stop navigation
    }
}