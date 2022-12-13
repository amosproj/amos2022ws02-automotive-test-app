package com.amos.infotaimos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentVehiclePropertiesPageBinding
import com.amos.infotaimos.viewmodel.VehiclePropertiesPageViewModel

class VehiclePropertiesPage : ViewBindingFragment<FragmentVehiclePropertiesPageBinding>() {
    private val viewModel: VehiclePropertiesPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permission = viewModel.getBatteryPermission(requireContext(), requireActivity())
        if(permission) {
            viewModel.setPropertyManager(requireContext())
            viewModel.registerBatteryCallback(binding)
            binding.batteryLevelTile.tileBatteryLevelText.text = viewModel.getBatteryLevel()
            binding.batteryProgressBar.min = 0
            binding.batteryProgressBar.max = viewModel.getCapacityWH().toInt()
            binding.batteryProgressBar.progress = viewModel.getBatteryWH().toInt()
            binding.batteryProgressText.text = viewModel.getBatteryText()
        }
        else {
            binding.batteryLevelTile.tileBatteryLevelText.text = "Can't access battery level"
        }
    }
}