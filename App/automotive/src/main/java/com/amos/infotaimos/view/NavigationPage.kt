package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentNavigationPageBinding
import com.amos.infotaimos.viewmodel.NavigationPageViewModel


class NavigationPage : ViewBindingFragment<FragmentNavigationPageBinding>() {
    private val viewModel: NavigationPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startNavigationButton.setOnClickListener {
            viewModel.startNavigation(requireContext(), getDelay())
        }
        binding.stopNavigationButton.setOnClickListener {
            viewModel.stopNavigation(requireContext(), getDelay())
        }
        binding.simulateSpeechAnnouncment.setOnClickListener {
            viewModel.speechAnnouncement(requireContext(), getDelay())
        }
    }

    private fun getDelay() : Long {
        val delayString = binding.delaySpinner.selectedItem as String
        var delay = 0L
        if (delayString == "30s")
            delay = 30000L
        else if (delayString == "1min")
            delay = 60000L
        return delay
    }
}
