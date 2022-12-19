package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentNavigationPageBinding
import com.amos.infotaimos.model.NavigationService
import com.amos.infotaimos.viewmodel.NavigationPageViewModel

class NavigationPage : ViewBindingFragment<FragmentNavigationPageBinding>() {
    private val viewModel: NavigationPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            startNavigationButton.setOnClickListener {
                viewModel.performNavigationAction(requireContext(), getDelay())
            }

            simulateSpeechAnnouncment.setOnClickListener {
                viewModel.speechAnnouncement(requireContext(), getDelay())
            }
        }

        NavigationService.apply {
            navIndicatorLiveData.observe(viewLifecycleOwner) {
                binding.navigationStatusText.text = if (it) "Navigation is active" else "Navigation is inactive"
                binding.navigationActionText.text = if (it) "Press to stop navigation" else "Press to start navigation"
            }

            countdown.observe(viewLifecycleOwner) {
                if (startTask != null) {
                    binding.navigationStatusText.text = "Starting navigation in $it seconds"
                    binding.navigationActionText.text = "Press to cancel"
                } else if (stopTask != null) {
                    binding.navigationStatusText.text = "Stopping navigation in $it seconds"
                    binding.navigationActionText.text = "Press to cancel"
                }
            }
        }
    }

    private fun getDelay(): Long {
        val delayString = binding.delaySpinner.selectedItem as String
        var delay = 0L
        if (delayString == "30s")
            delay = 30000L
        else if (delayString == "1min")
            delay = 60000L
        return delay
    }
}
