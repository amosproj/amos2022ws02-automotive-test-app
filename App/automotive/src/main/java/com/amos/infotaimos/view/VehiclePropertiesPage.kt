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
        viewModel.loadData(requireContext())
        viewModel.vin.observe(viewLifecycleOwner) { vin ->
            binding.vinTile.tileVinText.text = vin
        }

        binding.vinTile.tileVinEditButton.setOnClickListener {
            viewModel._vin.value = binding.vinTile.tileVinEditableText.text.toString()
            viewModel.saveData(requireContext(), binding.vinTile.tileVinText.text.toString())
            binding.vinTile.tileVinEditableText.text.clear()
        }
    }
}