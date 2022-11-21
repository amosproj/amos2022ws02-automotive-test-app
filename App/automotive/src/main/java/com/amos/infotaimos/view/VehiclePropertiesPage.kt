package com.amos.infotaimos.view

import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.FragmentVehiclePropertiesPageBinding
import com.amos.infotaimos.viewmodel.VehiclePropertiesPageViewModel

class VehiclePropertiesPage : ViewBindingFragment<FragmentVehiclePropertiesPageBinding>() {
    private val viewModel: VehiclePropertiesPageViewModel by viewModels()
}