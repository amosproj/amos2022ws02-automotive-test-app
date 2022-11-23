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
        binding.fragmentPowerButtonDisplayOff.setOnClickListener {
            viewModel.turnoffDisplay(requireContext())
        }
    }
}
