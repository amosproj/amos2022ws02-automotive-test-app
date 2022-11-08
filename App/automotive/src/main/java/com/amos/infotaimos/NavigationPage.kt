package com.amos.infotaimos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.databinding.FragmentNavigationPageBinding

class NavigationPage : ViewBindingFragment<FragmentNavigationPageBinding>() {
    private val viewModel: NavigationPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startNavigationButton.setOnClickListener {
            //get selected Delay
            val delayString = binding.delaySpinner.selectedItem as String
            var delay = 0L
            if (delayString == "30s")
                delay = 30000L
            else if(delayString == "1min")
                delay = 60000L
            //call startNavigation
            viewModel.startNavigation(requireContext(), delay)
        }
        binding.stopNavigationButton.setOnClickListener {
            val delayString = binding.delaySpinner.selectedItem as String
            var delay = 0L
            if (delayString == "30s")
                delay = 30000L
            else if (delayString == "1min")
                delay = 60000L
            viewModel.stopNavigation(requireContext(), delay)
        }
    }
}