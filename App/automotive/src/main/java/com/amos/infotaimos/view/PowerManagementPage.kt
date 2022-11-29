package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentPowerManagementPageBinding
import com.amos.infotaimos.viewmodel.PowerManagementPageViewModel

class PowerManagementPage : ViewBindingFragment<FragmentPowerManagementPageBinding>() {
    private val viewModel: PowerManagementPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentPowerButtonMuteSystem.setOnClickListener {
            // get selected Delay
            val delayString = binding.delaySpinner.selectedItem as String
            var delay = 0L
            if (delayString == "30s")
                delay = 30000L
            else if (delayString == "1min")
                delay = 60000L

            viewModel.toggleMuteSystem(requireContext(), delay)
        }

    }

}
