package com.amos.infotaimos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class NavigationPage : Fragment() {

    companion object {
        fun newInstance() = NavigationPage()
    }

    private lateinit var viewModel: NavigationPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startNavButton: Button = view.findViewById(R.id.start_navigation_button)
        startNavButton.setOnClickListener{
            println("funktioniert")
            viewModel.startNavigation(0)
        }
        // Set another on click listener for Stop navigation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavigationPageViewModel::class.java)
    }

}